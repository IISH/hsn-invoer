package org.iish.hsn.invoer.service.akte;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
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
        bevolkingsregisterFlowState.setOneLineEach(true);
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
                // First initialize the default person dynamics
                createNewPersonDynamics(bevolkingsregisterFlow, person);

                // Then obtain the stored person dynamics and replace the default initialized ones by these
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

                // If this is the RP, then update the pointer
                if (person.getNatureOfPerson() == Person.NatureOfPerson.FIRST_RP.getNatureOfPerson()) {
                    bevolkingsregisterFlow.setVolgnrOP(person.getRp());
                }
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
     * Set up a (new) person for registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void setUpPerson(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        Ref_GBH refGbh = bevolkingsregisterFlow.getRefGbh();
        List<Person> b2 = bevolkingsregisterFlow.getB2();
        List<Integer> correctionPersons = bevolkingsregisterFlow.getCorrectionPersons();

        int curPersonKey = bevolkingsregisterFlow.getCurPersonKey();
        int nextPersonKey = bevolkingsregisterFlow.getNextPersonKey();
        int correctionPersonKeyIdx = correctionPersons.indexOf(curPersonKey);

        // Check if the user indicated to go back to a previous person first
        if ((nextPersonKey > 0) && (nextPersonKey <= b2.size())) {
            bevolkingsregisterFlow.setCurB2Index(nextPersonKey - 1);
            bevolkingsregisterFlow.setNextPersonKey(0);
        }
        // In case of correction: which people do we need to correct?
        else if (bevolkingsregisterFlow.isCorrection() && (correctionPersons.size() > 0) &&
                ((curPersonKey == 0) || (correctionPersonKeyIdx <= correctionPersons.size() - 2))) {
            int newPersonKey = correctionPersons.get(correctionPersonKeyIdx + 1);
            bevolkingsregisterFlow.setCurB2Index(newPersonKey - 1);
            bevolkingsregisterFlow.setCurPersonKey(newPersonKey);
        }
        // In case we do not correct: are we moving back to a previously entered person to correct?
        else if (!bevolkingsregisterFlow.isCorrection() && ((curPersonKey + 1) <= b2.size())) {
            int newPersonKey = curPersonKey + 1;
            bevolkingsregisterFlow.setCurB2Index(newPersonKey - 1);
            bevolkingsregisterFlow.setCurPersonKey(newPersonKey);

            // Update the person with data from the previous person
            updatePersonData(bevolkingsregisterFlow, bevolkingsregisterFlow.getCurB2());
        }
        // Otherwise we have to add a new person to this registration
        else {
            Person person = new Person(b2.size() + 1);
            person.setRegistrationId(bevolkingsregisterFlow.getB4().getRegistrationId());

            // If this person is the OP, take over information from the birth certificate
            if (person.getKeyToRegistrationPersons() == bevolkingsregisterFlow.getVolgnrOP()) {
                person.setNatureOfPerson(Person.NatureOfPerson.FIRST_RP.getNatureOfPerson());

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
                person.setNatureOfPerson(Person.NatureOfPerson.NO_RP.getNatureOfPerson());
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

            // Update the person with data from the previous person
            // In case all persons are edited at once, let JavaScript take care of that
            if (bevolkingsregisterFlow.isOneLineEach()) {
                updatePersonData(bevolkingsregisterFlow, person);
            }

            // Find out whether this person was referenced before for the burgelijke stand
            List<PersonDynamic> relatedPersonDynamics = bevolkingsregisterHelper
                    .getWhereRelatedPerson(bevolkingsregisterFlow, person.getRp(), PersonDynamic.Type.BURGELIJKE_STAND);
            for (PersonDynamic relatedPersonDynamic : relatedPersonDynamics) {
                int type = relatedPersonDynamic.getContentOfDynamicData();
                Person relatedPerson = b2.get(relatedPersonDynamic.getKeyToRegistrationPersons() - 1);
                PersonDynamic personDynamic =
                        bevolkingsregisterFlow.getB3ForType(PersonDynamic.Type.BURGELIJKE_STAND).get(person.getRp())
                                .get(0);

                // Related person is a widow, so this person must have been married to the related person and died
                if (type == 2) {
                    personDynamic.setContentOfDynamicData(5);
                    personDynamic.setValueOfRelatedPerson(relatedPerson.getRp());

                    person.setDayOfDecease(relatedPersonDynamic.getDayOfMutation());
                    person.setMonthOfDecease(relatedPersonDynamic.getMonthOfMutation());
                    person.setYearOfDecease(relatedPersonDynamic.getYearOfMutation());
                }
                // Related person is married
                else if (type == 3) {
                    // Related person is married and dead, so this person must be a widow
                    if (bevolkingsregisterHelper.hasOverlijdensData(relatedPerson).equals("j")) {
                        personDynamic.setContentOfDynamicData(2);
                        personDynamic.setValueOfRelatedPerson(relatedPerson.getRp());

                        personDynamic.setDayOfMutation(relatedPerson.getDayOfDecease());
                        personDynamic.setMonthOfMutation(relatedPerson.getMonthOfDecease());
                        personDynamic.setYearOfMutation(relatedPerson.getYearOfDecease());
                    }
                    // Otherwise the related person is still married to this person
                    else {
                        personDynamic.setContentOfDynamicData(5);
                        personDynamic.setValueOfRelatedPerson(relatedPerson.getRp());

                        personDynamic.setDayOfMutation(relatedPersonDynamic.getDayOfMutation());
                        personDynamic.setMonthOfMutation(relatedPersonDynamic.getMonthOfMutation());
                        personDynamic.setYearOfMutation(relatedPersonDynamic.getYearOfMutation());
                    }
                }
                else {
                    personDynamic.setContentOfDynamicData(type);
                    personDynamic.setValueOfRelatedPerson(relatedPerson.getRp());

                    personDynamic.setDayOfMutation(relatedPersonDynamic.getDayOfMutation());
                    personDynamic.setMonthOfMutation(relatedPersonDynamic.getMonthOfMutation());
                    personDynamic.setYearOfMutation(relatedPersonDynamic.getYearOfMutation());
                }
            }

            b2.add(person.getRp() - 1, person);
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
                    List<PersonDynamic> b3ForPerson = b3.get(prevPerson.getRp());
                    PersonDynamic newPersonDynamic;

                    // If there is no initial value (usually herkomst/vertrek) then create one
                    if (b3ForPerson.isEmpty()) {
                        newPersonDynamic = createPersonDynamic(bevolkingsregisterFlow, newPerson, type, 1);
                    }
                    else {
                        newPersonDynamic = b3ForPerson.get(0);
                    }

                    BeanUtils.copyProperties(prevPersonDynamic, newPersonDynamic, "registrationId", "recordID");
                    newPersonDynamic.setRegistrationId(curRegistrationId);

                    if (b3ForPerson.isEmpty()) {
                        b3ForPerson.add(newPersonDynamic);
                    }
                    else {
                        b3ForPerson.set(0, newPersonDynamic);
                    }
                }
            }

            // Make sure the first herkomst/vertrek pointers are set to these records, if created
            updateFirstHerkomst(bevolkingsregisterFlow, newPerson);
            updateFirstVertrek(bevolkingsregisterFlow, newPerson);
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

        // Also loop over the persons to make sure the intial dynamic properties records are created
        for (Person person : bevolkingsregisterFlow.getB2()) {
            createNewPersonDynamics(bevolkingsregisterFlow, person);
        }

        // Now make sure we reset the pointer of the current person to the first person
        bevolkingsregisterFlow.setCurB2Index(0);
        bevolkingsregisterFlow.setCurPersonKey(1);
    }

    /**
     * Selects the persons of the registration that should be corrected.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param data                   Contains the persons that should be corrected as indicated by the user.
     */
    public void selectPersonsForCorrection(BevolkingsregisterFlowState bevolkingsregisterFlow,
                                           Map<String, Object> data) {
        List<Person> b2 = bevolkingsregisterFlow.getB2();
        List<Integer> correctionPersons = bevolkingsregisterFlow.getCorrectionPersons();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            // The persons for correction have a key ('person' + key)
            // and 'x' as value in case the person has to be corrected
            if (entry.getKey().startsWith("person") && entry.getValue().toString().equalsIgnoreCase("x")) {
                String personKey = entry.getKey().replace("person", "");

                // Make sure the person key is a valid integer and is a known person in this registration
                if (personKey.matches("^\\d+$")) {
                    Integer person = Integer.parseInt(personKey);
                    if (person <= b2.size()) {
                        correctionPersons.add(person);
                    }
                }
            }
        }
    }

    /**
     * Registers the updated identification data.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void registerIdentification(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        Registration registration = bevolkingsregisterFlow.getB4();
        Person newOp = bevolkingsregisterFlow.getOp();
        RegistrationId registrationId = registration.getRegistrationId();

        // Update all other records with the new registration id
        for (Person person : bevolkingsregisterFlow.getB2()) {
            person.setRegistrationId(registrationId);

            // Also update the nature of this person as the line of the RP might have changed
            if (newOp != null) {
                if (person.getRp() == newOp.getRp()) {
                    person.setNatureOfPerson(Person.NatureOfPerson.FIRST_RP.getNatureOfPerson());
                }
                else if ((person.getDayOfBirth() == newOp.getDayOfBirth()) &&
                        (person.getMonthOfBirth() == newOp.getMonthOfBirth()) &&
                        (person.getYearOfBirth() == newOp.getYearOfBirth())) {
                    person.setNatureOfPerson(Person.NatureOfPerson.NEXT_RP.getNatureOfPerson());
                }
                else {
                    person.setNatureOfPerson(Person.NatureOfPerson.NO_RP.getNatureOfPerson());
                }
            }
        }

        for (PersonDynamic personDynamic : bevolkingsregisterFlow.getAllB3()) {
            personDynamic.setRegistrationId(registrationId);
        }

        for (RegistrationAddress registrationAddress : bevolkingsregisterFlow.getB6()) {
            registrationAddress.setRegistrationId(registrationId);
        }
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
     * Saves the complete registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void saveRegistration(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        registerAndSaveRegistration(bevolkingsregisterFlow);
        registerAndSavePersons(bevolkingsregisterFlow);
        registerAndSaveRegistrationAddresses(bevolkingsregisterFlow);
    }

    /**
     * Deletes the current bevolkingsregister registration.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     */
    public void deleteRegistration(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        Registration b4 = bevolkingsregisterFlow.getB4();
        WorkOrder workOrder = b4.getWorkOrder();
        RegistrationId registrationId = b4.getRegistrationId();

        registrationRepository.delete(b4);
        personRepository.delete(registrationId, workOrder);
        personDynamicRepository.delete(registrationId, workOrder);
        registrationAddressRepository.delete(registrationId, workOrder);
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
                // First one is without a date
                if (sequenceNumber == 1) {
                    personDynamic.setDayOfMutation(-3);
                    personDynamic.setMonthOfMutation(-3);
                    personDynamic.setYearOfMutation(-3);
                }
                // No relation allowed if type is 'C'
                if (refAinb.getTypeRegister().equals("C")) {
                    personDynamic.setContentOfDynamicData(-3);
                }
                // First one is usually the head
                if (person.getKeyToRegistrationPersons() == 1) {
                    personDynamic.setContentOfDynamicData(1);
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
     * Initializes the default first dynamic properties for a person (if it does not exist yet).
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
                    if (!firstB3Her.containsKey(keyToPerson)) {
                        PersonDynamic her = createPersonDynamic(bevolkingsregisterFlow, person, type, 1);
                        firstB3Her.put(keyToPerson, her);
                        b3Her.put(keyToPerson, new ArrayList<PersonDynamic>());
                    }
                    break;
                case VERTREK:
                    Map<Integer, PersonDynamic> firstB3Ver = bevolkingsregisterFlow.getFirstB3Ver();
                    Map<Integer, List<PersonDynamic>> b3Ver = bevolkingsregisterFlow.getB3Ver();
                    if (!firstB3Ver.containsKey(keyToPerson)) {
                        PersonDynamic ver = createPersonDynamic(bevolkingsregisterFlow, person, type, 1);
                        firstB3Ver.put(keyToPerson, ver);
                        b3Ver.put(keyToPerson, new ArrayList<PersonDynamic>());
                    }
                    break;
                default:
                    Map<Integer, List<PersonDynamic>> b3 = bevolkingsregisterFlow.getB3ForType(type);
                    if (!b3.containsKey(keyToPerson) || b3.get(keyToPerson).isEmpty()) {
                        PersonDynamic personDynamic = createPersonDynamic(bevolkingsregisterFlow, person, type, 1);
                        b3.put(keyToPerson, new ArrayList<>(Arrays.asList(personDynamic)));
                    }
            }
        }
    }

    /**
     * Updates the person with some data from the previous person.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     */
    private void updatePersonData(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        Ref_AINB refAinb = bevolkingsregisterFlow.getRefAinb();
        List<Person> b2 = bevolkingsregisterFlow.getB2();

        PersonDynamic kg = bevolkingsregisterFlow.getB3Kg().get(person.getRp()).get(0);
        PersonDynamic her = bevolkingsregisterFlow.getFirstB3Her().get(person.getRp());
        PersonDynamic ver = bevolkingsregisterFlow.getFirstB3Ver().get(person.getRp());

        // Copy information from the prev person (if there is a prev person)
        int prevPerson = person.getKeyToRegistrationPersons() - 1;
        if (prevPerson > 0) {
            Person lastPerson = b2.get(prevPerson - 1);

            person.setDayOfRegistration(lastPerson.getDayOfRegistration());
            person.setMonthOfRegistration(lastPerson.getMonthOfRegistration());
            person.setYearOfRegistration(lastPerson.getYearOfRegistration());

            if ((person.getFamilyName() == null) || person.getFamilyName().trim().isEmpty()) {
                person.setFamilyName(lastPerson.getFamilyName());
            }
            if ((person.getPlaceOfBirth() == null) || person.getPlaceOfBirth().trim().isEmpty()) {
                person.setPlaceOfBirth(lastPerson.getPlaceOfBirth());
            }

            person.setNationality(lastPerson.getNationality());
            if (refAinb.getTypeRegister().equals("C")) {
                person.setLegalPlaceOfLivingInCodes(lastPerson.getLegalPlaceOfLivingInCodes());
            }
            else {
                person.setLegalPlaceOfLiving(lastPerson.getLegalPlaceOfLiving());
            }

            // Do the same for dynamic data of type kerkgenootschap, herkomst en vertrek
            Map<Integer, List<PersonDynamic>> prevB3Kg = bevolkingsregisterFlow.getB3Kg();
            if (prevB3Kg.containsKey(prevPerson)) {
                PersonDynamic prevKg = prevB3Kg.get(prevPerson).get(0);
                kg.setDynamicData2(prevKg.getDynamicData2());
            }

            Map<Integer, PersonDynamic> prevB3Her = bevolkingsregisterFlow.getFirstB3Her();
            if (prevB3Her.containsKey(prevPerson)) {
                PersonDynamic prevHerkomst = prevB3Her.get(prevPerson);

                her.setDynamicData2(prevHerkomst.getDynamicData2());
                her.setDayOfMutation(prevHerkomst.getDayOfMutation());
                her.setMonthOfMutation(prevHerkomst.getMonthOfMutation());
                her.setYearOfMutation(prevHerkomst.getYearOfMutation());

                updateHerkomst(bevolkingsregisterFlow, person);
            }

            Map<Integer, PersonDynamic> prevB3Ver = bevolkingsregisterFlow.getFirstB3Ver();
            if (prevB3Ver.containsKey(prevPerson)) {
                PersonDynamic prevVertrek = prevB3Ver.get(prevPerson);

                ver.setDynamicData2(prevVertrek.getDynamicData2());
                ver.setDayOfMutation(prevVertrek.getDayOfMutation());
                ver.setMonthOfMutation(prevVertrek.getMonthOfMutation());
                ver.setYearOfMutation(prevVertrek.getYearOfMutation());

                updateVertrek(bevolkingsregisterFlow, person);
            }
        }
    }

    /**
     * Synchronizes the herkomst data.
     * See if the first record is filled out, if so, update the first record in the complete list as well.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     */
    private void updateHerkomst(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        updateHerkomstVertrek(person, bevolkingsregisterFlow.getB3Her(), bevolkingsregisterFlow.getFirstB3Her());
    }

    /**
     * Synchronizes the vertrek data.
     * See if the first record is filled out, if so, update the first record in the complete list as well.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     */
    private void updateVertrek(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        updateHerkomstVertrek(person, bevolkingsregisterFlow.getB3Ver(), bevolkingsregisterFlow.getFirstB3Ver());
    }

    /**
     * Synchronizes the herkomst data.
     * Check the complete list to see whether the first record exists.
     * If so, make the first record point to this record.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     */
    private void updateFirstHerkomst(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        updateFirstHerkomstVertrek(bevolkingsregisterFlow, person, PersonDynamic.Type.HERKOMST,
                bevolkingsregisterFlow.getB3Her(), bevolkingsregisterFlow.getFirstB3Her());
    }

    /**
     * Synchronizes the vertrek data.
     * Check the complete list to see whether the first record exists.
     * If so, make the first record point to this record.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     */
    private void updateFirstVertrek(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        updateFirstHerkomstVertrek(bevolkingsregisterFlow, person, PersonDynamic.Type.VERTREK,
                bevolkingsregisterFlow.getB3Ver(), bevolkingsregisterFlow.getFirstB3Ver());
    }

    /**
     * Synchronizes the herkomst/vertrek data.
     * See if the first record is filled out, if so, update the first record in the complete list as well.
     *
     * @param person  The person in question.
     * @param b3      The complete list of herkomst or vetrek data from all persons.
     * @param firstB3 All first herkomst/vertrek records from all persons.
     */
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

    /**
     * Synchronizes the herkomst/vertrek data.
     * Check the complete list to see whether the first record exists.
     * If so, make the first record point to this record.
     *
     * @param bevolkingsregisterFlow The bevolkingsregister flow state.
     * @param person                 The person in question.
     * @param type                   Either herkomst or vertrek.
     * @param b3                     The complete list of herkomst or vetrek data from all persons.
     * @param firstB3                All first herkomst/vertrek records from all persons.
     */
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