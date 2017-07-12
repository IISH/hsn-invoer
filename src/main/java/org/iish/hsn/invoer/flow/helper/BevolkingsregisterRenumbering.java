package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.bev.Person;
import org.iish.hsn.invoer.domain.invoer.bev.PersonDynamic;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationAddress;
import org.iish.hsn.invoer.flow.state.BevolkingsregisterFlowState;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Takes care of the renumbering of persons.
 */
public class BevolkingsregisterRenumbering {
    private BevolkingsregisterFlowState original;
    private BevolkingsregisterFlowState renumbered;

    private Set<Person>              deletedB2;
    private Set<PersonDynamic>       deletedB3;
    private Set<RegistrationAddress> deletedB6;

    private List<Integer> missingKeys;

    /**
     * Starts the renumbering of persons.
     *
     * @param original   The original records. B2 (persons) already contains the new keys,
     *                   but the array indexes are still incorrect (are still set to the original key).
     * @param renumbered The renumbered records. Whether a person is renumbered or deleted depends on the size
     *                   of the B2 list. B2 should be initialized by records from the original lists.
     *                   B3 and B6 should be empty.
     */
    public BevolkingsregisterRenumbering(BevolkingsregisterFlowState original, BevolkingsregisterFlowState renumbered) {
        this.original = original;
        this.renumbered = renumbered;

        this.deletedB2 = new HashSet<>();
        this.deletedB3 = new HashSet<>();
        this.deletedB6 = new HashSet<>();

        this.missingKeys = new ArrayList<>();

        // Already initialize the missing keys list, we remove those that are not missing later
        for (int i=1; i<=renumbered.getB2().size(); i++) {
            missingKeys.add(i);
        }
    }

    public Set<Person> getDeletedB2() {
        return deletedB2;
    }

    public Set<PersonDynamic> getDeletedB3() {
        return deletedB3;
    }

    public Set<RegistrationAddress> getDeletedB6() {
        return deletedB6;
    }

    public List<Integer> getMissingKeys() {
        return missingKeys;
    }

    public void renumber() {
        // Renumber the existing persons from the original list and move them to the other lists
        for (Person person : original.getB2()) {
            int keyOriginal = original.getB2().indexOf(person) + 1;
            int keyNew = person.getRp();

            Person newPerson = new Person();
            BeanUtils.copyProperties(person, newPerson);
            newPerson.setPreviousRp(keyOriginal);

            // If the new key is 0 or does not fit in the new list, then remove the person from the list
            if ((keyNew == 0) || (keyNew > renumbered.getB2().size())) {
                if (newPerson.getId() != null) {
                    deletedB2.add(newPerson);
                }

                renumberPersonDynamics(keyOriginal, keyNew, true);
                renumberRegistrationAddresses(keyOriginal, keyNew, true);
            }
            else {
                renumbered.getB2().set(keyNew - 1, newPerson);
                missingKeys.remove((Integer) keyNew); // Casting to make sure the right method is called

                renumberPersonDynamics(keyOriginal, keyNew, false);
                renumberRegistrationAddresses(keyOriginal, keyNew, false);
            }
        }
    }

    private void renumberPersonDynamics(int keyOriginal, int keyNew, boolean isDeleted) {
        for (PersonDynamic.Type type : PersonDynamic.Type.values()) {
            for (PersonDynamic personDynamic : original.getB3ForType(type).get(keyOriginal)) {
                if (isDeleted) {
                    deletePersonDynamic(personDynamic);
                }
                else {
                    renumberPersonDynamic(personDynamic, type, keyNew);
                }
            }
        }
    }

    private void renumberPersonDynamic(PersonDynamic personDynamic, PersonDynamic.Type type, int keyNew) {
        PersonDynamic newPersonDynamic = new PersonDynamic();
        BeanUtils.copyProperties(personDynamic, newPersonDynamic);

        newPersonDynamic.setKeyToRegistrationPersons(keyNew);

        // Also update the value of the related person
        int relatedPerson = newPersonDynamic.getValueOfRelatedPerson();
        if (relatedPerson > 0) {
            // Set the new key of the related person, if the person is now deleted, initialize the value back to -1
            int newKeyRelatedPerson = original.getB2().get(relatedPerson - 1).getRp();
            if ((newKeyRelatedPerson == 0) || (newKeyRelatedPerson > renumbered.getB2().size())) {
                newPersonDynamic.setValueOfRelatedPerson(-1);
            }
            else {
                newPersonDynamic.setValueOfRelatedPerson(newKeyRelatedPerson);
            }
        }

        Map<Integer, List<PersonDynamic>> b3Renumbered = renumbered.getB3ForType(type);
        b3Renumbered.get(keyNew).add(newPersonDynamic);
    }

    private void deletePersonDynamic(PersonDynamic personDynamic) {
        // If it has no record id, it is not stored in the database, so just forget about it
        if (personDynamic.getId() != null) {
            PersonDynamic newPersonDynamic = new PersonDynamic();
            BeanUtils.copyProperties(personDynamic, newPersonDynamic);
            deletedB3.add(newPersonDynamic);
        }
    }

    private void renumberRegistrationAddresses(int keyOriginal, int keyNew, boolean isDeleted) {
        for (RegistrationAddress registrationAddress : original.getB6()) {
            if (registrationAddress.getKeyToRegistrationPersons() == keyOriginal) {
                RegistrationAddress newRegistrationAddress = new RegistrationAddress();
                BeanUtils.copyProperties(registrationAddress, newRegistrationAddress);

                // If it has no record id and will be deleted, it is not stored in the database, so just forget about it
                if (isDeleted && (newRegistrationAddress.getId() != null)) {
                    deletedB6.add(newRegistrationAddress);
                }
                else {
                    newRegistrationAddress.setKeyToRegistrationPersons(keyNew);
                    renumbered.getB6().add(newRegistrationAddress);
                }
            }
        }
    }
}
