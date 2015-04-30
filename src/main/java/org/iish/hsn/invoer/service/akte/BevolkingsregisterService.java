package org.iish.hsn.invoer.service.akte;

import org.iish.hsn.invoer.domain.invoer.bev.*;
import org.iish.hsn.invoer.domain.reference.Ref_AINB;
import org.iish.hsn.invoer.domain.reference.Ref_GBH;
import org.iish.hsn.invoer.exception.AkteException;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.flow.helper.BevolkingsregisterHelper;
import org.iish.hsn.invoer.flow.helper.BevolkingsregisterRenumbering;
import org.iish.hsn.invoer.flow.state.*;
import org.iish.hsn.invoer.repository.invoer.bev.PersonDynamicRepository;
import org.iish.hsn.invoer.repository.invoer.bev.PersonRepository;
import org.iish.hsn.invoer.repository.invoer.bev.RegistrationAddressRepository;
import org.iish.hsn.invoer.repository.invoer.bev.RegistrationRepository;
import org.iish.hsn.invoer.service.LookupService;
import org.iish.hsn.invoer.util.InputMetadata;
import org.iish.hsn.invoer.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;

import java.util.*;

/**
 * Deals with the preparation and storage of various elements of the 'bevolkingsregister'.
 */
@Service
public class BevolkingsregisterService {
    @Autowired private InputMetadata                 inputMetadata;
    @Autowired private LookupService                 lookupService;
    @Autowired private RegistrationRepository        registrationRepository;
    @Autowired private PersonRepository              personRepository;
    @Autowired private PersonDynamicRepository       personDynamicRepository;
    @Autowired private RegistrationAddressRepository registrationAddressRepository;
    @Autowired private BevolkingsregisterHelper      bevolkingsregisterHelper;

    /**
     * Creates a new bevolkingsregister flow state for new input (one line for each person)
     * with all required domain objects.
     *
     * @return A new bevolkingsregister flow state.
     */
    public BevolkingsregisterFlowState createNewAkteForNewInputOneLineEach() {
        BevolkingsregisterFlowState bevolkingsregisterFlowState = createNewAkte();
        bevolkingsregisterFlowState.setCorrection(false);
        bevolkingsregisterFlowState.setOneLineEach(true);
        return bevolkingsregisterFlowState;
    }

    /**
     * Creates a new bevolkingsregister flow state for new input (all lines at once for each person)
     * with all required domain objects.
     *
     * @return A new bevolkingsregister flow state.
     */
    public BevolkingsregisterFlowState createNewAkteForNewInputAllLines() {
        BevolkingsregisterFlowState bevolkingsregisterFlowState = createNewAkte();
        bevolkingsregisterFlowState.setCorrection(false);
        bevolkingsregisterFlowState.setOneLineEach(false);
        return bevolkingsregisterFlowState;
    }

    /**
     * Creates a new bevolkingsregister flow state for correction with all required domain objects.
     *
     * @return A new bevolkingsregister flow state.
     */
    public BevolkingsregisterFlowState createNewAkteForCorrection() {
        BevolkingsregisterFlowState bevolkingsregisterFlowState = createNewAkte();
        bevolkingsregisterFlowState.setCorrection(true);
        bevolkingsregisterFlowState.setOneLineEach(false);
        return bevolkingsregisterFlowState;
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has the bevolkingsregister.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void registerOP(BevolkingsregisterFlowState bevolkingsregisterFlow) throws AkteException {
        try {
            Registration b4 = bevolkingsregisterFlow.getB4();

            Ref_GBH refGbh = lookupService.getRefGbh(b4.getRegistrationId().getKeyToRP(), true);
            bevolkingsregisterFlow.setRefGbh(refGbh);

            Ref_AINB refAinb = lookupService.getRefAinb(b4.getRegistrationId().getKeyToSourceRegister(), true);
            bevolkingsregisterFlow.setRefAinb(refAinb);

            // Do we have a previous registration we want to start with?
            RegistrationId prevRegistration = bevolkingsregisterFlow.getPrevRegistration();
            if (prevRegistration.getKeyToSourceRegister() > 0 && prevRegistration.getDayEntryHead() > 0 &&
                prevRegistration.getMonthEntryHead() > 0 && prevRegistration.getYearEntryHead() > 0) {
                setUpWithPrevRegistration(bevolkingsregisterFlow);
            }
            else {
                // Set up all persons if the user will enter all persons at once
                // Otherwise set up one person per screen
                if (bevolkingsregisterFlow.isOneLineEach()) {
                    setUpPerson(bevolkingsregisterFlow);
                }
                else {
                    for (int i = 0; i < bevolkingsregisterFlow.getNoRegels(); i++) {
                        setUpPerson(bevolkingsregisterFlow);
                    }
                }
            }

            registerAndSaveRegistration(bevolkingsregisterFlow);
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has to edit the bevolkingsregister.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void editOP(BevolkingsregisterFlowState bevolkingsregisterFlow) throws AkteException {
        try {
            RegistrationId registrationId = bevolkingsregisterFlow.getB4().getRegistrationId();

            bevolkingsregisterFlow.setRefGbh(lookupService.getRefGbh(registrationId.getKeyToRP(), true));
            bevolkingsregisterFlow.setRefAinb(lookupService.getRefAinb(registrationId.getKeyToSourceRegister(), true));

            bevolkingsregisterFlow.setB4(
                    registrationRepository.findByRegistrationIdAndWorkOrder(
                            registrationId, inputMetadata.getWorkOrder()));

            List<Person> b2 =
                    personRepository.findByRegistrationIdAndWorkOrder(registrationId, inputMetadata.getWorkOrder());
            if (b2 != null) {
                bevolkingsregisterFlow.setB2(new ArrayList<>(b2));
            }

            for (Person person : bevolkingsregisterFlow.getB2()) {
                createNewPersonDynamics(bevolkingsregisterFlow, person);

                List<PersonDynamic> personDynamics = personDynamicRepository
                        .findAllPersonDynamicForPerson(registrationId, person.getRp(), inputMetadata.getWorkOrder());
                for (PersonDynamic personDynamic : personDynamics) {
                    Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow
                            .getB3ForType(PersonDynamic.Type.getType(personDynamic.getDynamicDataType()));
                    List<PersonDynamic> b3ForPerson = b3.get(person.getRp());

                    // This is the first of the persons dynamic type,
                    // so remove the default initialized which is not necessary anymore
                    if (personDynamic.getDynamicDataSequenceNumber() == 1) {
                        b3ForPerson.clear();
                    }
                    b3ForPerson.add(personDynamic.getDynamicDataSequenceNumber() - 1, personDynamic);
                }

                updateFirstHerkomst(bevolkingsregisterFlow, person);
                updateFirstVertrek(bevolkingsregisterFlow, person);
            }

            List<RegistrationAddress> b6 =
                    registrationAddressRepository.findByRegistrationId(registrationId, inputMetadata.getWorkOrder());
            if (b6 != null) {
                bevolkingsregisterFlow.setB6(new ArrayList<>(b6));
            }

            bevolkingsregisterFlow.setCurB2Index(0);
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Set up a new person for registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void setUpPerson(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        Ref_GBH refGbh = bevolkingsregisterFlow.getRefGbh();
        List<Person> b2 = bevolkingsregisterFlow.getB2();

        // Check if the user indicated to go back to a previous person first
        // If not, initialize a new person if there is no person created yet
        int nextPersonKey = bevolkingsregisterFlow.getNextPersonKey();
        if ((nextPersonKey > 0) && (nextPersonKey <= b2.size())) {
            bevolkingsregisterFlow.setCurB2Index(nextPersonKey - 1);
            bevolkingsregisterFlow.setNextPersonKey(0);
        }
        else if ((bevolkingsregisterFlow.getCurPersonKey() + 1) <= b2.size()) {
            int newPersonKey = bevolkingsregisterFlow.getCurPersonKey() + 1;
            bevolkingsregisterFlow.setCurB2Index(newPersonKey - 1);
            bevolkingsregisterFlow.setCurPersonKey(newPersonKey);
        }
        else {
            Person person = new Person(b2.size() + 1);
            person.setRegistrationId(bevolkingsregisterFlow.getB4().getRegistrationId());

            // If this person is the OP, take over information from the birth certificate
            if (person.getKeyToRegistrationPersons() == bevolkingsregisterFlow.getVolgnrOP()) {
                person.setNatureOfPerson(1); // First OP

                String firstName =
                        Utils.getFirstNames(refGbh.getFirstName1(), refGbh.getFirstName2(), refGbh.getFirstName3());

                person.setFamilyName(refGbh.getLastName());
                person.setFirstName(firstName);
                person.setSex(refGbh.getSex());
                person.setPlaceOfBirth(refGbh.getNameMunicipality());
                person.setDayOfBirth(refGbh.getDayOfBirth());
                person.setMonthOfBirth(refGbh.getMonthOfBirth());
                person.setYearOfBirth(refGbh.getYearOfBirth());
            }
            else {
                person.setNatureOfPerson(2); // No OP
            }

            // Only fill out the information of the OPs parents if the OP is the third or later in the list
            if (bevolkingsregisterFlow.getVolgnrOP() >= 3) {
                // First one is the father
                if (person.getKeyToRegistrationPersons() == 1) {
                    String firstName = Utils.getFirstNames(refGbh.getFirstName1Father(), refGbh.getFirstName2Father(),
                                                           refGbh.getFirstName3Father());

                    person.setFamilyName(refGbh.getLastNameFather());
                    person.setFirstName(firstName);
                    person.setSex("m");
                }

                // Second one is the mother
                if (person.getKeyToRegistrationPersons() == 2) {
                    String firstName = Utils.getFirstNames(refGbh.getFirstName1Mother(), refGbh.getFirstName2Mother(),
                                                           refGbh.getFirstName3Mother());

                    person.setFamilyName(refGbh.getLastNameMother());
                    person.setFirstName(firstName);
                    person.setSex("v");
                }
            }

            // Set some default values
            person.setDayOfRegistration(-1);
            person.setMonthOfRegistration(-1);
            person.setYearOfRegistration(-1);

            // Create initial dynamic values for this person
            createNewPersonDynamics(bevolkingsregisterFlow, person);

            b2.add(person.getKeyToRegistrationPersons() - 1, person);
            bevolkingsregisterFlow.setCurB2Index(person.getRp() - 1);
            bevolkingsregisterFlow.setCurPersonKey(person.getRp());
        }
    }

    /**
     * Set up a new persons for registration using a previous registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void setUpWithPrevRegistration(BevolkingsregisterFlowState bevolkingsregisterFlow) throws NotFoundException {
        RegistrationId curRegistrationId = bevolkingsregisterFlow.getB4().getRegistrationId();
        RegistrationId prevRegistrationId = bevolkingsregisterFlow.getPrevRegistration();

        prevRegistrationId.setKeyToRP(curRegistrationId.getKeyToRP());
        List<Person> prevPersons =
                personRepository.findByRegistrationIdAndWorkOrder(prevRegistrationId, inputMetadata.getWorkOrder());

        // Clone the persons and the first of their dynamic properties
        List<Person> b2 = bevolkingsregisterFlow.getB2();
        for (Person prevPerson : prevPersons) {
            // First clone the person
            Person newPerson = new Person();

            BeanUtils.copyProperties(prevPerson, newPerson, "registrationId", "recordID");
            newPerson.setRegistrationId(curRegistrationId);
            b2.add(newPerson.getRp() - 1, newPerson);

            // Already create initial dynamic values for this person
            createNewPersonDynamics(bevolkingsregisterFlow, newPerson);

            // Then clone the first of their dynamic properties
            for (PersonDynamic.Type type : PersonDynamic.Type.values()) {
                PersonDynamic prevPersonDynamic = personDynamicRepository
                        .findFirstPersonDynamicForPerson(prevRegistrationId, prevPerson.getRp(), type.getType(),
                                                         inputMetadata.getWorkOrder());

                if (prevPersonDynamic != null) {
                    Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow.getB3ForType(type);
                    PersonDynamic newPersonDynamic = b3.get(prevPerson.getRp()).get(0);

                    BeanUtils.copyProperties(prevPersonDynamic, newPersonDynamic, "registrationId", "recordID");
                    newPersonDynamic.setRegistrationId(curRegistrationId);
                    b3.get(newPerson.getRp()).set(0, newPersonDynamic);
                }
            }
        }
    }

    /**
     * Renumber the persons of the registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void renumberRegistrationPersons(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        BevolkingsregisterFlowState renumbered = createNewAkte();
        renumbered.setRefGbh(bevolkingsregisterFlow.getRefGbh());
        renumbered.setRefAinb(bevolkingsregisterFlow.getRefAinb());

        // First figure out how many lines we need at a minimum at this moment
        // If the user will enter all persons at once, the number is fixed and already entered by the user
        int noLines = bevolkingsregisterFlow.getNoRegels();
        if (bevolkingsregisterFlow.isOneLineEach()) {
            for (Person person : bevolkingsregisterFlow.getB2()) {
                if (person.getRp() > noLines) {
                    noLines = person.getRp();
                }
            }
        }

        // Now initialize the list with persons first
        for (int i = 0; i < noLines; i++) {
            setUpPerson(renumbered);
        }

        // Make sure person dynamic is also properly initialized
        for (PersonDynamic.Type type : PersonDynamic.Type.values()) {
            Map<Integer, List<PersonDynamic>> b3Renumbered = renumbered.getB3ForType(type);
            for (int i = 0; i < noLines; i++) {
                if (i == 0) {
                    b3Renumbered.clear();
                }
                b3Renumbered.put(i + 1, new ArrayList<PersonDynamic>());
            }
        }

        // Start renumbering
        BevolkingsregisterRenumbering bevolkingsregisterRenumbering =
                new BevolkingsregisterRenumbering(bevolkingsregisterFlow, renumbered);
        bevolkingsregisterRenumbering.renumber();

        // Now replace the original with the renumbered
        bevolkingsregisterFlow.setB2(renumbered.getB2());
        bevolkingsregisterFlow.setB6(renumbered.getB6());
        for (PersonDynamic.Type type : PersonDynamic.Type.values()) {
            Map<Integer, List<PersonDynamic>> originalB3 = bevolkingsregisterFlow.getB3ForType(type);
            originalB3.clear();
            originalB3.putAll(renumbered.getB3ForType(type));
        }

        // And delete the deleted records also in the database
        personRepository.delete(bevolkingsregisterRenumbering.getDeletedB2());
        personDynamicRepository.delete(bevolkingsregisterRenumbering.getDeletedB3());
        registrationAddressRepository.delete(bevolkingsregisterRenumbering.getDeletedB6());
    }

    /**
     * Register all persons from the registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void registerPersons(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        for (Person person : bevolkingsregisterFlow.getB2()) {
            registerPerson(bevolkingsregisterFlow, person);
        }
    }

    /**
     * Register the current person from the registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void registerPerson(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        registerPerson(bevolkingsregisterFlow, bevolkingsregisterFlow.getCurB2());
    }

    /**
     * Register the given person from the registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person to register.
     */
    public void registerPerson(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        String typeRegister = bevolkingsregisterFlow.getRefAinb().getTypeRegister();
        if (typeRegister.equalsIgnoreCase("C") || typeRegister.equalsIgnoreCase("D")) {
            person.setDayOfRegistration(-3);
            person.setMonthOfRegistration(-3);
            person.setYearOfRegistration(-3);
        }

        // In case this is a 'fake' RP
        if (person.getFamilyName().trim().equalsIgnoreCase("GEEN OP")) {
            person.setFirstName("");

            person.setDayOfRegistration(-3);
            person.setMonthOfRegistration(-3);
            person.setYearOfRegistration(-3);

            person.setSex("o");
            person.setPlaceOfBirth("");

            person.setDayOfBirth(-1);
            person.setMonthOfBirth(-1);
            person.setYearOfBirth(-1);

            // Delete all dynamic properties of this person
            for (PersonDynamic.Type type : PersonDynamic.Type.values()) {
                Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow.getB3ForType(type);
                personDynamicRepository.delete(b3.get(person.getKeyToRegistrationPersons()));
                b3.put(person.getKeyToRegistrationPersons(), new ArrayList<PersonDynamic>());
            }

            PersonDynamic b3Her = bevolkingsregisterFlow.getFirstB3Her().get(person.getKeyToRegistrationPersons());
            b3Her.setDayOfMutation(0);

            PersonDynamic b3Ver = bevolkingsregisterFlow.getFirstB3Ver().get(person.getKeyToRegistrationPersons());
            b3Ver.setDayOfMutation(0);

            // Reset person dynamics with new instances
            PersonDynamic personDynamicRel =
                    createPersonDynamic(bevolkingsregisterFlow, person, PersonDynamic.Type.RELATIE_TOV_HOOFD, 1);
            personDynamicRel.setContentOfDynamicData(-1);
            bevolkingsregisterFlow.getB3Rel().get(person.getKeyToRegistrationPersons()).add(personDynamicRel);

            PersonDynamic personDynamicBrg =
                    createPersonDynamic(bevolkingsregisterFlow, person, PersonDynamic.Type.BURGELIJKE_STAND, 1);
            personDynamicBrg.setContentOfDynamicData(6);
            bevolkingsregisterFlow.getB3Brg().get(person.getKeyToRegistrationPersons()).add(personDynamicBrg);

            PersonDynamic personDynamicKg =
                    createPersonDynamic(bevolkingsregisterFlow, person, PersonDynamic.Type.KERKGENOOTSCHAP, 1);
            personDynamicKg.setDynamicData2("N");
            bevolkingsregisterFlow.getB3Kg().get(person.getKeyToRegistrationPersons()).add(personDynamicKg);

            PersonDynamic personDynamicBrp =
                    createPersonDynamic(bevolkingsregisterFlow, person, PersonDynamic.Type.BEROEP, 1);
            bevolkingsregisterFlow.getB3Brp().get(person.getKeyToRegistrationPersons()).add(personDynamicBrp);
        }

        updateHerkomst(bevolkingsregisterFlow, person);
        updateVertrek(bevolkingsregisterFlow, person);

        registerPersonDynamics(bevolkingsregisterFlow);
    }

    /**
     * Register the dynamic properties of the current person from the registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void registerPersonDynamics(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        Person person = bevolkingsregisterFlow.getCurB2();

        // Make sure the first relation is without a date
        List<PersonDynamic> b3Rel = bevolkingsregisterFlow.getB3Rel().get(person.getKeyToRegistrationPersons());
        for (PersonDynamic rel : b3Rel) {
            if (rel.getDynamicDataSequenceNumber() == 1) {
                rel.setDayOfMutation(-3);
                rel.setMonthOfMutation(-3);
                rel.setYearOfMutation(-3);
            }
        }

        // Synchronize the first herkomst/vertrek instances
        updateFirstHerkomst(bevolkingsregisterFlow, person);
        updateFirstVertrek(bevolkingsregisterFlow, person);
    }

    /**
     * Register the registration addresses.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void registerRegistrationAddresses(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        List<RegistrationAddress> b6 = bevolkingsregisterFlow.getB6();
        Collections.sort(b6);

        // Renumber the sequence numbers, we already sorted the collection
        Map<Integer, Integer> sequenceNumbersPerPerson = new HashMap<>();
        for (RegistrationAddress registrationAddress : b6) {
            int person = registrationAddress.getKeyToRegistrationPersons();

            int seqNr = 0;
            if (sequenceNumbersPerPerson.containsKey(person)) {
                seqNr = sequenceNumbersPerPerson.get(person);
            }

            int newSeqNr = seqNr + 1;
            sequenceNumbersPerPerson.put(person, newSeqNr);
            registrationAddress.setSequenceNumberToAddresses(newSeqNr);

            // Also set the registration id right away
            registrationAddress.setRegistrationId(bevolkingsregisterFlow.getB4().getRegistrationId());
        }
    }

    /**
     * Register and save the registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void registerAndSaveRegistration(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        Registration b4 = bevolkingsregisterFlow.getB4();
        inputMetadata.saveToEntity(b4);
        b4 = registrationRepository.save(b4);
        bevolkingsregisterFlow.setB4(b4);
    }

    /**
     * Register and save all persons from the registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void registerAndSavePersons(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        for (Person person : bevolkingsregisterFlow.getB2()) {
            registerAndSavePerson(bevolkingsregisterFlow, person);
        }
        registerAndSaveRegistration(bevolkingsregisterFlow);
    }

    /**
     * Register and save the current person from the registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void registerAndSavePerson(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        registerAndSavePerson(bevolkingsregisterFlow, bevolkingsregisterFlow.getCurB2());
    }

    /**
     * Register and save the given person from the registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person to register and save.
     */
    public void registerAndSavePerson(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        registerPerson(bevolkingsregisterFlow, person);
        int personKey = person.getKeyToRegistrationPersons();

        inputMetadata.saveToEntity(person);
        person = personRepository.save(person);
        bevolkingsregisterFlow.getB2().set(personKey - 1, person);

        for (PersonDynamic.Type type : PersonDynamic.Type.values()) {
            saveDynamicFields(bevolkingsregisterFlow, personKey, type);
        }
    }

    /**
     * Save a registration address.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void registerAndSaveRegistrationAddresses(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        registerRegistrationAddresses(bevolkingsregisterFlow);
        List<RegistrationAddress> b6 = bevolkingsregisterFlow.getB6();

        for (RegistrationAddress registrationAddress : b6) {
            inputMetadata.saveToEntity(registrationAddress);
        }

        b6 = registrationAddressRepository.save(b6);
        bevolkingsregisterFlow.setB6(b6);

        registerAndSaveRegistration(bevolkingsregisterFlow);
    }

    /**
     * Adds a new dynamic field for a person.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person of which to update the dynamic fields in question.
     * @param type                   The type of dynamic fields to update.
     */
    public void updateDynamicField(BevolkingsregisterFlowState bevolkingsregisterFlow, int person,
                                   PersonDynamic.Type type, int sequenceNumber, Map<String, Object> data) {
        bevolkingsregisterFlow.setCurB2Index(person - 1);

        Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow.getB3ForType(type);
        List<PersonDynamic> b3ForPerson = b3.get(person);
        PersonDynamic personDynamic = b3ForPerson.get(sequenceNumber - 1);

        DataBinder binder = new DataBinder(personDynamic);
        binder.bind(new MutablePropertyValues(data));

        registerPersonDynamics(bevolkingsregisterFlow);
    }

    /**
     * Adds a new dynamic field for a person.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person of which to update the dynamic fields in question.
     * @param type                   The type of dynamic fields to update.
     */
    public void addNewDynamicField(BevolkingsregisterFlowState bevolkingsregisterFlow, int person,
                                   PersonDynamic.Type type, Map<String, Object> data) {
        bevolkingsregisterFlow.setCurB2Index(person - 1);

        Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow.getB3ForType(type);
        List<PersonDynamic> b3ForPerson = b3.get(person);
        int sequenceNumber = b3ForPerson.size() + 1;

        Person b2 = bevolkingsregisterFlow.getB2().get(person - 1);
        b3ForPerson.add(createPersonDynamic(bevolkingsregisterFlow, b2, type, sequenceNumber));

        updateDynamicField(bevolkingsregisterFlow, person, type, sequenceNumber, data);
    }

    /**
     * Removes a dynamic field of a person.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person of which to update the dynamic fields in question.
     * @param type                   The type of dynamic fields to update.
     * @param sequenceNumber         The sequence number to remove.
     */
    public void removeDynamicField(BevolkingsregisterFlowState bevolkingsregisterFlow, int person,
                                   PersonDynamic.Type type, int sequenceNumber) {
        bevolkingsregisterFlow.setCurB2Index(person - 1);

        Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow.getB3ForType(type);
        List<PersonDynamic> b3ForPerson = b3.get(person);
        b3ForPerson.remove(sequenceNumber - 1);

        // Renumber the sequence numbers
        int newSequenceNumber = 1;
        List<PersonDynamic> newB3ForPerson = new ArrayList<>();
        for (PersonDynamic personDynamic : b3ForPerson) {
            personDynamic.setDynamicDataSequenceNumber(newSequenceNumber++);
            newB3ForPerson.add(personDynamic);
        }
        b3.put(person, newB3ForPerson);

        registerPersonDynamics(bevolkingsregisterFlow);
    }

    /**
     * Save the dynamic fields of a person.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person of which to update the dynamic fields in question.
     * @param type                   The type of dynamic fields to update.
     */
    public void saveDynamicFields(BevolkingsregisterFlowState bevolkingsregisterFlow, int person,
                                  PersonDynamic.Type type) {
        Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow.getB3ForType(type);
        List<PersonDynamic> personDynamicList = b3.get(person);

        for (PersonDynamic personDynamic : personDynamicList) {
            inputMetadata.saveToEntity(personDynamic);
        }

        personDynamicList = personDynamicRepository.save(personDynamicList);
        b3.put(person, personDynamicList);
    }

    /**
     * Adds a new registration address for a person.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     * @param seqNr                  The sequence number in question.
     * @param data                   The data to bind to the registration address.
     */
    public void updateRegistrationAddress(BevolkingsregisterFlowState bevolkingsregisterFlow, int person, int seqNr,
                                          Map<String, Object> data) {
        List<RegistrationAddress> b6 = bevolkingsregisterFlow.getB6();
        RegistrationAddress registrationAddress = bevolkingsregisterHelper.getRegistrationAddressFor(b6, person, seqNr);

        DataBinder binder = new DataBinder(registrationAddress);
        binder.bind(new MutablePropertyValues(data));

        registerRegistrationAddresses(bevolkingsregisterFlow);
    }

    /**
     * Adds a new registration address.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     * @param seqNr                  The sequence number in question.
     * @param data                   The data to bind to the registration address.
     */
    public void addNewRegistrationAddress(BevolkingsregisterFlowState bevolkingsregisterFlow, int person, int seqNr,
                                          Map<String, Object> data) {
        List<RegistrationAddress> b6 = bevolkingsregisterFlow.getB6();
        b6.add(new RegistrationAddress(person, seqNr));

        updateRegistrationAddress(bevolkingsregisterFlow, person, seqNr, data);
    }

    /**
     * Removes a registration address.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     * @param seqNr                  The sequence number in question.
     */
    public void removeRegistrationAddress(BevolkingsregisterFlowState bevolkingsregisterFlow, int person, int seqNr) {
        List<RegistrationAddress> b6 = bevolkingsregisterFlow.getB6();
        RegistrationAddress registrationAddress = bevolkingsregisterHelper.getRegistrationAddressFor(b6, person, seqNr);

        b6.remove(registrationAddress);
        registrationAddressRepository.delete(registrationAddress);

        registerRegistrationAddresses(bevolkingsregisterFlow);
    }

    /**
     * Update the number of lines (persons) of the bevolkingsregister.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param totalNr                The updated total number of lines.
     * @throws AkteException Thrown when this action means the RP is deleted as well.
     */
    public void updateNrOfPersons(BevolkingsregisterFlowState bevolkingsregisterFlow, int totalNr)
            throws AkteException {
        List<Person> b2 = bevolkingsregisterFlow.getB2();
        if (totalNr >= b2.size()) {
            bevolkingsregisterFlow.setNoRegels(totalNr);
            for (int i = b2.size(); i < totalNr; i++) {
                setUpPerson(bevolkingsregisterFlow);
            }
        }
        else if (totalNr < bevolkingsregisterFlow.getVolgnrOP()) {
            throw new AkteException("The RP cannot be deleted.");
        }
        else {
            bevolkingsregisterFlow.setNoRegels(totalNr);
            for (int i = totalNr; i < b2.size(); i++) {
                b2.get(i).setKeyToRegistrationPersons(0);
            }
            renumberRegistrationPersons(bevolkingsregisterFlow);
        }
    }

    /**
     * Copy the data from one line to another.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person to update.
     * @param lineToCopy             The line (person) of which to copy the data.
     * @throws AkteException Thrown when either the person or the line to copy do not exist.
     */
    public void copyLine(BevolkingsregisterFlowState bevolkingsregisterFlow, int person, int lineToCopy)
            throws AkteException {
        List<Person> b2 = bevolkingsregisterFlow.getB2();

        if (person > b2.size()) {
            throw new AkteException("The person in question is not created yet.");
        }
        if (lineToCopy > b2.size()) {
            throw new AkteException("The line to copy from is not created yet.");
        }

        Person to = b2.get(person - 1);
        Person from = b2.get(lineToCopy - 1);
        BeanUtils.copyProperties(from, to, "RecordID", "keyToRegistrationPersons");

        for (PersonDynamic.Type type : PersonDynamic.Type.values()) {
            switch (type) {
                case HERKOMST:
                    Map<Integer, PersonDynamic> b3Her = bevolkingsregisterFlow.getFirstB3Her();
                    BeanUtils.copyProperties(b3Her.get(lineToCopy), b3Her.get(person), "RecordID",
                                             "keyToRegistrationPersons");
                    break;
                case VERTREK:
                    Map<Integer, PersonDynamic> b3Ver = bevolkingsregisterFlow.getFirstB3Ver();
                    BeanUtils.copyProperties(b3Ver.get(lineToCopy), b3Ver.get(person), "RecordID",
                                             "keyToRegistrationPersons");
                    break;
                default:
                    Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow.getB3ForType(type);
                    List<PersonDynamic> b3To = b3.get(person);
                    List<PersonDynamic> b3From = b3.get(lineToCopy);
                    if (b3To.size() > 0) {
                        if (b3From.isEmpty()) {
                            b3From.add(createPersonDynamic(bevolkingsregisterFlow, to, type, 1));
                        }
                        PersonDynamic personDynamicTo = b3To.get(0);
                        PersonDynamic personDynamicFrom = b3From.get(0);
                        BeanUtils.copyProperties(personDynamicFrom, personDynamicTo, "RecordID",
                                                 "keyToRegistrationPersons");
                    }
            }
        }
    }

    /**
     * Deletes the current bevolkingsregister registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void deleteRegistration(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        registrationRepository.delete(bevolkingsregisterFlow.getB4());
        personRepository.delete(bevolkingsregisterFlow.getB2());
        personDynamicRepository.delete(bevolkingsregisterFlow.getAllB3());
        registrationAddressRepository.delete(bevolkingsregisterFlow.getB6());
    }

    /**
     * Creates a new bevolkingsregister flow state with all required domain objects.
     *
     * @return A new bevolkingsregister flow state.
     */
    private BevolkingsregisterFlowState createNewAkte() {
        Registration b4 = new Registration();
        List<Person> b2 = new ArrayList<>();
        List<RegistrationAddress> b6 = new ArrayList<>();

        Map<Integer, List<PersonDynamic>> b3Rel = new HashMap<>();
        Map<Integer, List<PersonDynamic>> b3Brg = new HashMap<>();
        Map<Integer, List<PersonDynamic>> b3Kg = new HashMap<>();
        Map<Integer, List<PersonDynamic>> b3Brp = new HashMap<>();
        Map<Integer, List<PersonDynamic>> b3Her = new HashMap<>();
        Map<Integer, List<PersonDynamic>> b3Ver = new HashMap<>();

        Map<Integer, PersonDynamic> firstB3Her = new HashMap<>();
        Map<Integer, PersonDynamic> firstB3Ver = new HashMap<>();

        BevolkingsregisterFlowState bevolkingsregisterFlowState =
                new BevolkingsregisterFlowState(b4, b2, b6, b3Rel, b3Brg, b3Kg, b3Brp, b3Her, b3Ver, firstB3Her,
                                                firstB3Ver);

        bevolkingsregisterFlowState.setRefAinb(new Ref_AINB());
        bevolkingsregisterFlowState.setRefGbh(new Ref_GBH());

        return bevolkingsregisterFlowState;
    }

    /**
     * Creates a new record for dynamic properties of a person.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     * @param type                   The type of dynamic properties.
     * @param sequenceNumber         The sequence number.
     * @return A new PersonDynamic.
     */
    private PersonDynamic createPersonDynamic(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person,
                                              PersonDynamic.Type type, int sequenceNumber) {
        Ref_AINB refAinb = bevolkingsregisterFlow.getRefAinb();
        PersonDynamic personDynamic = new PersonDynamic(person.getKeyToRegistrationPersons(), type, sequenceNumber);

        // First set type specific default values
        switch (type) {
            case RELATIE_TOV_HOOFD:
                // First one is usually the head
                if (person.getKeyToRegistrationPersons() == 1) {
                    personDynamic.setContentOfDynamicData(1);
                }
                // First one is without a date
                if (sequenceNumber == 1) {
                    personDynamic.setDayOfMutation(-3);
                    personDynamic.setMonthOfMutation(-3);
                    personDynamic.setYearOfMutation(-3);
                }
                if (refAinb.getTypeRegister().equals("C")) {
                    personDynamic.setContentOfDynamicData(-3);
                }
                break;
            case KERKGENOOTSCHAP:
            case BEROEP:
                // These types have no date
                personDynamic.setDayOfMutation(-3);
                personDynamic.setMonthOfMutation(-3);
                personDynamic.setYearOfMutation(-3);
                break;
        }

        personDynamic.setRegistrationId(person.getRegistrationId());
        personDynamic.setNatureOfPerson(person.getNatureOfPerson());
        personDynamic.setValueOfRelatedPerson(-1);

        return personDynamic;
    }

    /**
     * Initializes the default first dynamic properties for a person.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     */
    private void createNewPersonDynamics(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        int keyToPerson = person.getKeyToRegistrationPersons();
        for (PersonDynamic.Type type : PersonDynamic.Type.values()) {
            switch (type) {
                case HERKOMST:
                    Map<Integer, PersonDynamic> firstB3Her = bevolkingsregisterFlow.getFirstB3Her();
                    Map<Integer, List<PersonDynamic>> b3Her = bevolkingsregisterFlow.getB3Her();
                    PersonDynamic her = createPersonDynamic(bevolkingsregisterFlow, person, type, 1);
                    firstB3Her.put(keyToPerson, her);
                    b3Her.put(keyToPerson, new ArrayList<PersonDynamic>());
                    break;
                case VERTREK:
                    Map<Integer, PersonDynamic> firstB3Ver = bevolkingsregisterFlow.getFirstB3Ver();
                    Map<Integer, List<PersonDynamic>> b3Ver = bevolkingsregisterFlow.getB3Ver();
                    PersonDynamic ver = createPersonDynamic(bevolkingsregisterFlow, person, type, 1);
                    firstB3Ver.put(keyToPerson, ver);
                    b3Ver.put(keyToPerson, new ArrayList<PersonDynamic>());
                    break;
                default:
                    Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow.getB3ForType(type);
                    PersonDynamic personDynamic = createPersonDynamic(bevolkingsregisterFlow, person, type, 1);
                    b3.put(keyToPerson, new ArrayList<>(Arrays.asList(personDynamic)));
            }
        }
    }

    private void updateHerkomst(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        updateHerkomstVertrek(person, bevolkingsregisterFlow.getB3Her(), bevolkingsregisterFlow.getFirstB3Her());
    }

    private void updateVertrek(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        updateHerkomstVertrek(person, bevolkingsregisterFlow.getB3Ver(), bevolkingsregisterFlow.getFirstB3Ver());
    }

    private void updateFirstHerkomst(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        updateFirstHerkomstVertrek(bevolkingsregisterFlow, person, PersonDynamic.Type.HERKOMST,
                                   bevolkingsregisterFlow.getB3Her(), bevolkingsregisterFlow.getFirstB3Her());
    }

    private void updateFirstVertrek(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        updateFirstHerkomstVertrek(bevolkingsregisterFlow, person, PersonDynamic.Type.VERTREK,
                                   bevolkingsregisterFlow.getB3Ver(), bevolkingsregisterFlow.getFirstB3Ver());
    }

    private void updateHerkomstVertrek(Person person, Map<Integer, List<PersonDynamic>> b3,
                                       Map<Integer, PersonDynamic> firstB3) {
        List<PersonDynamic> personDynamics = b3.get(person.getKeyToRegistrationPersons());
        PersonDynamic personDynamic = firstB3.get(person.getKeyToRegistrationPersons());
        if (personDynamic.getDayOfMutation() == 0) {
            personDynamicRepository.delete(personDynamics);
            b3.put(person.getKeyToRegistrationPersons(), new ArrayList<PersonDynamic>());
        }
        else if (personDynamics.size() > 0) {
            personDynamics.set(0, personDynamic);
        }
        else {
            personDynamics.add(0, personDynamic);
        }
    }

    private void updateFirstHerkomstVertrek(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person,
                                            PersonDynamic.Type type, Map<Integer, List<PersonDynamic>> b3,
                                            Map<Integer, PersonDynamic> firstB3) {
        List<PersonDynamic> personDynamics = b3.get(person.getKeyToRegistrationPersons());
        PersonDynamic personDynamic = createPersonDynamic(bevolkingsregisterFlow, person, type, 1);
        if (personDynamics.size() > 0) {
            personDynamic = personDynamics.get(0);
        }
        firstB3.put(person.getKeyToRegistrationPersons(), personDynamic);
    }
}