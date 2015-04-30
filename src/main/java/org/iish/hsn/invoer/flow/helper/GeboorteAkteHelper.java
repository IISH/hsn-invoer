package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.geb.Gebkant;
import org.iish.hsn.invoer.domain.invoer.geb.Gebknd;
import org.iish.hsn.invoer.domain.invoer.pick.Plaats;
import org.iish.hsn.invoer.domain.reference.Stpb;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.service.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

@Component
public class GeboorteAkteHelper {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMMM yyyy");

    @Autowired
    private LookupService lookupService;

    public String getGemeente(Stpb stpb) {
        try {
            if (stpb != null) {
                if ((stpb.getGemeente() != null) && !stpb.getGemeente().trim().isEmpty()) {
                    return stpb.getGemeente();
                }

                Plaats plaats = lookupService.getPlaats(stpb.getGemnr(), true);
                return plaats.getGemnaam();
            }
            return null;
        }
        catch (NotFoundException nfe) {
            return null;
        }
    }

    public String getDatum(Gebknd gebknd) {
        if (gebknd.getAktedag() > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(gebknd.getJaar(), gebknd.getAktemnd() - 1, gebknd.getAktedag());
            return DATE_FORMAT.format(calendar.getTime());
        }
        else {
            return "AKTEJAAR: " + gebknd.getJaar();
        }
    }

    public String getMoederEchtgenoteAangever(Gebknd gebknd) {
        String brgstmr = gebknd.getBrgstmr();
        if ((brgstmr == null) || brgstmr.trim().isEmpty()) {
            return "";
        }
        if (brgstmr.equals("5")) {
            return "j";
        }
        if (Arrays.asList(1,2,3,4,6,7,8).contains(new Integer(brgstmr))) {
            return "n";
        }

        return "";
    }

    public String getKantmeldingDatum(Gebkant gebkant) {
        if ((gebkant.getKantdag() != 0) || (gebkant.getKantmnd() != 0) || (gebkant.getKantjr() != 0)) {
            if ((gebkant.getKantdag() > 0) || (gebkant.getKantmnd() > 0) || (gebkant.getKantjr() > 0)) {
                return "j";
            }
            if ((gebkant.getKantdag() == -1) || (gebkant.getKantmnd() == -1) || (gebkant.getKantjr() == -1)) {
                return "n";
            }
        }
        return "";
    }

    public String getKantmeldingBesluitDatum(Gebkant gebkant) {
        if ((gebkant.getKwyzdag() != 0) || (gebkant.getKwyzmnd() != 0) || (gebkant.getKwyzjr() != 0)) {
            if ((gebkant.getKwyzdag() > 0) || (gebkant.getKwyzmnd() > 0) || (gebkant.getKwyzjr() > 0)) {
                return "j";
            }
            if ((gebkant.getKwyzdag() == -1) || (gebkant.getKwyzmnd() == -1) || (gebkant.getKwyzjr() == -1)) {
                return "n";
            }
        }
        return "";
    }

    public String getKantmeldingNaamsverandering(Gebkant gebkant, boolean isCorrection) {

        if (    ((gebkant.getKanmgeb() != null) && !gebkant.getKanmgeb().trim().isEmpty()) ||
                ((gebkant.getKvrn1geb() != null) && !gebkant.getKvrn1geb().trim().isEmpty()) ||
                ((gebkant.getKvrn2geb() != null) && !gebkant.getKvrn2geb().trim().isEmpty()) ||
                ((gebkant.getKvrn3geb() != null) && !gebkant.getKvrn3geb().trim().isEmpty())) {
            return "j";
        }

        if (isCorrection) {
            return "n";
        }

        return "";
    }

    public String getKantmeldingGeslachtsverandering(Gebkant gebkant, boolean isCorrection) {
        if ((gebkant.getKsexgeb() != null) && !gebkant.getKsexgeb().trim().isEmpty()) {
            return "j";
        }
        if (isCorrection) {
            return "n";
        }
        return "";
    }
}
