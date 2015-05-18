package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.bev.Person;
import org.iish.hsn.invoer.domain.invoer.bev.PersonDynamic;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationAddress;
import org.iish.hsn.invoer.domain.reference.Ref_AINB;
import org.iish.hsn.invoer.flow.state.BevolkingsregisterFlowState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BevolkingsregisterHelper {
    private static final Pattern DATE_EXPLICIET_HOOFD = Pattern.compile("^###\\$([0-9]{2})/([0-9]{2})/([0-9]{4})$");

    public boolean registrationRequiresAnotherPerson(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        List<Person> b2 = bevolkingsregisterFlow.getB2();
        int curPerson = bevolkingsregisterFlow.getCurPersonKey();
        int volgnrOP = bevolkingsregisterFlow.getVolgnrOP();

        // If we are in correction, require another person as long as not all correction persons were corrected
        if (bevolkingsregisterFlow.isCorrection()) {
            List<Integer> correctionPersons = bevolkingsregisterFlow.getCorrectionPersons();
            int correctionPersonKeyIdx = correctionPersons.indexOf(curPerson);

            return ((correctionPersonKeyIdx <= (b2.size() - 2)) && (correctionPersonKeyIdx >= 0));
        }

        // Otherwise, if the RP is not yet entered, another person is required
        return (curPerson < volgnrOP);
    }

    public boolean registrationHasMaxPersons(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        Ref_AINB refAinb = bevolkingsregisterFlow.getRefAinb();
        List<Person> b2 = bevolkingsregisterFlow.getB2();
        int curPerson = bevolkingsregisterFlow.getCurPersonKey();

        // If we are in correction, there is no max as long as not all correction persons were corrected
        if (bevolkingsregisterFlow.isCorrection()) {
            List<Integer> correctionPersons = bevolkingsregisterFlow.getCorrectionPersons();
            int correctionPersonKeyIdx = correctionPersons.indexOf(curPerson);

            if ((correctionPersonKeyIdx <= (b2.size() - 2)) && (correctionPersonKeyIdx >= 0)) {
                return false;
            }
        }

        // Only one person is allowed in case of an A or I register (correction may lead to multiple persons though)
        return ((curPerson >= 1) && (refAinb.getTypeRegister().equals("A") || refAinb.getTypeRegister().equals("I")));
    }

    public List<PersonDynamic> getPersonDynamicsWithInvalidBurgStandRelatie(BevolkingsregisterFlowState
                                                                                    bevolkingsregisterFlow) {
        List<PersonDynamic> personDynamics = new ArrayList<>();
        List<PersonDynamic> b3 = bevolkingsregisterFlow.getAllB3ForType(PersonDynamic.Type.BURGELIJKE_STAND);
        int nrOfPersons = bevolkingsregisterFlow.getB2().size();

        for (PersonDynamic personDynamic : b3) {
            if (personDynamic.getValueOfRelatedPerson() > nrOfPersons) {
                personDynamics.add(personDynamic);
            }
        }

        return personDynamics;
    }

    public Set<Integer> getRelatedPersonsOfFirstPerson(List<PersonDynamic> personDynamics) {
        Integer person = -1;
        Set<Integer> relatedPersons = new HashSet<>();

        for (PersonDynamic personDynamic : personDynamics) {
            if (person == -1) {
                person = personDynamic.getKeyToRegistrationPersons();
            }
            if (personDynamic.getKeyToRegistrationPersons() == person) {
                relatedPersons.add(personDynamic.getValueOfRelatedPerson());
            }
        }

        return relatedPersons;
    }

    public int findKeyOfRp(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        for (Person person : bevolkingsregisterFlow.getB2()) {
            if (person.getNatureOfPerson() == Person.NatureOfPerson.FIRST_RP.getNatureOfPerson()) {
                return person.getRp();
            }
        }
        return 0;
    }

    public boolean isKaartSysteem(Ref_AINB refAinb) {
        String typeRegister = refAinb.getTypeRegister();
        return (typeRegister.equalsIgnoreCase("I") || typeRegister.equalsIgnoreCase("G"));
    }

    public String getPositieFromCode(PersonDynamic b3) {
        int code = b3.getContentOfDynamicData();
        switch (code) {
            case 1:
                return "h";
            case 2:
                return "o";
            case 3:
                return "n";
            default:
                return "";
        }
    }

    public int getDayFromDateExplicietHoofd(PersonDynamic b3) {
        int[] date = getDateExplicietHoofd(b3);
        return date[0];
    }

    public int getMonthFromDateExplicietHoofd(PersonDynamic b3) {
        int[] date = getDateExplicietHoofd(b3);
        return date[1];
    }

    public int getYearFromDateExplicietHoofd(PersonDynamic b3) {
        int[] date = getDateExplicietHoofd(b3);
        return date[2];
    }

    public String hasInschrijfDatum(Person person) {
        int day = person.getDayOfRegistration();
        int month = person.getMonthOfRegistration();
        int year = person.getYearOfRegistration();

        if ((day == -2) && (month == -2) && (year == -2)) {
            return "j";
        }
        if ((day <= 0) && (month <= 0) && (year <= 0)) {
            return "n";
        }
        return "j";
    }

    public String hasHerkomstData(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        int personKey = person.getKeyToRegistrationPersons();
        List<PersonDynamic> b3Her = bevolkingsregisterFlow.getB3Her().get(personKey);
        if ((b3Her != null) && (b3Her.size() > 0)) {
            return "j";
        }
        return "n";
    }

    public String hasVertrekData(BevolkingsregisterFlowState bevolkingsregisterFlow, Person person) {
        int personKey = person.getKeyToRegistrationPersons();
        List<PersonDynamic> b3Ver = bevolkingsregisterFlow.getB3Ver().get(personKey);
        if ((b3Ver != null) && (b3Ver.size() > 0)) {
            return "j";
        }
        return "n";
    }

    public String hasOverlijdensData(Person person) {
        if ((person.getDayOfDecease() == 0) && (person.getMonthOfDecease() == 0) && (person.getYearOfDecease() == 0)) {
            return "n";
        }
        return "j";
    }

    public RegistrationAddress getRegistrationAddressFor(List<RegistrationAddress> registrationAddresses, int person,
                                                         int seqNr) {
        for (RegistrationAddress registrationAddress : registrationAddresses) {
            if (registrationAddress.getKeyToRegistrationPersons() == person && registrationAddress
                    .getSequenceNumberToAddresses() == seqNr) {
                return registrationAddress;
            }
        }
        return null;
    }

    public List<PersonDynamic> getWhereRelatedPerson(BevolkingsregisterFlowState bevolkingsregisterFlow, int person) {
        List<PersonDynamic> relatedPersonDynamics = new ArrayList<>();
        for (PersonDynamic personDynamic : bevolkingsregisterFlow.getAllB3()) {
            if (personDynamic.getValueOfRelatedPerson() == person) {
                relatedPersonDynamics.add(personDynamic);
            }
        }
        return relatedPersonDynamics;
    }

    public List<PersonDynamic> getWhereRelatedPerson(BevolkingsregisterFlowState bevolkingsregisterFlow, int person,
                                                     PersonDynamic.Type type) {
        List<PersonDynamic> relatedPersonDynamics = new ArrayList<>();
        for (PersonDynamic personDynamic : getWhereRelatedPerson(bevolkingsregisterFlow, person)) {
            if (personDynamic.getDynamicDataType() == type.getType()) {
                relatedPersonDynamics.add(personDynamic);
            }
        }
        return relatedPersonDynamics;
    }

    private int[] getDateExplicietHoofd(PersonDynamic b3) {
        String date = b3.getDynamicData2();
        if (date != null) {
            Matcher matcher = DATE_EXPLICIET_HOOFD.matcher(date);
            if (matcher.matches()) {
                return new int[]{
                        new Integer(matcher.group(1)), new Integer(matcher.group(2)), new Integer(matcher.group(3))
                };
            }
        }
        return new int[]{0, 0, 0};
    }
}
