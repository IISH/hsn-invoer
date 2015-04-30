package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.bev.Person;
import org.iish.hsn.invoer.domain.invoer.bev.PersonDynamic;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationAddress;
import org.iish.hsn.invoer.domain.reference.Ref_AINB;
import org.iish.hsn.invoer.flow.state.BevolkingsregisterFlowState;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BevolkingsregisterHelper {
    private static final Pattern DATE_EXPLICIET_HOOFD = Pattern.compile("^###\\$([0-9]{2})/([0-9]{2})/([0-9]{4})$");

    public int findKeyOfRp(BevolkingsregisterFlowState bevolkingsregisterFlow) {
        for (Person person : bevolkingsregisterFlow.getB2()) {
            if (person.getNatureOfPerson() == 1) {
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

    public RegistrationAddress getRegistrationAddressFor(List<RegistrationAddress> registrationAddresses, int person,
                                                         int seqNr) {
        for (RegistrationAddress registrationAddress : registrationAddresses) {
            if (registrationAddress.getKeyToRegistrationPersons() == person &&
                registrationAddress.getSequenceNumberToAddresses() == seqNr) {
                return registrationAddress;
            }
        }
        return null;
    }

    private int[] getDateExplicietHoofd(PersonDynamic b3) {
        String date = b3.getDynamicData2();
        if (date != null) {
            Matcher matcher = DATE_EXPLICIET_HOOFD.matcher(date);
            if (matcher.matches()) {
                return new int[]{new Integer(matcher.group(1)), new Integer(matcher.group(2)),
                                 new Integer(matcher.group(3))};
            }
        }
        return new int[]{0, 0, 0};
    }
}
