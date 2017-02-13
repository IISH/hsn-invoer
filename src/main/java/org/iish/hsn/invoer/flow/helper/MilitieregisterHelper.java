package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict;
import org.iish.hsn.invoer.flow.state.MilitieregisterFlowState;
import org.springframework.stereotype.Component;

@Component
public class MilitieregisterHelper {
    public String getTypeRegister(String type) {
        switch (type) {
            case "A":
                return "Alfabetische naamlijst";
            case "I":
                return "Inschrijvingsregister";
            case "K":
                return "Keuringsregister";
            case "L":
                return "Lotingsregister";
            default:
                return "Niet bekend";
        }
    }

    public String hasDataOnVoogd(Milition mil, boolean isCorrection) {
        if (!mil.getFirstNameGuardian().isEmpty() || !mil.getFamilyNameGuardian().isEmpty() ||
                !mil.getPlaceGuardian().isEmpty() || !mil.getProfessionGuardian().isEmpty())
            return "j";
        if (isCorrection)
            return "n";
        return "";
    }

    public String hasKenmerken(Milition mil, boolean isCorrection) {
        if (!mil.getFace().isEmpty() || !mil.getForehead().isEmpty() || !mil.getEyes().isEmpty() ||
                !mil.getNose().isEmpty() || !mil.getMouth().isEmpty() || !mil.getChin().isEmpty() ||
                !mil.getHair().isEmpty() || !mil.getEyebrows().isEmpty() || !mil.getNotableSigns().isEmpty())
            return "j";

        if (isCorrection)
            return "n";
        return "";
    }

    public String hasUitgesteld(MilitieregisterFlowState akte, boolean isCorrection) {
        if (checkVerdict(akte.getVerdictUitstel()))
            return "j";
        if (checkVerdict(akte.getVerdictTweedeUitstel()))
            return "j";

        if (isCorrection)
            return "n";
        return "";
    }

    public String hasInBeroep(MilitieregisterFlowState akte, boolean isCorrection) {
        if (checkVerdict(akte.getVerdictBezwaren()))
            return "j";
        if (checkVerdict(akte.getVerdictWet()))
            return "j";
        if (checkVerdict(akte.getVerdictKoning()))
            return "j";

        if (isCorrection)
            return "n";
        return "";
    }

    private boolean checkVerdict(Verdict verdict) {
        return (verdict != null) && ((verdict.getDayOfVerdict() != 0) ||
                (verdict.getMonthOfVerdict() != 0) || (verdict.getYearOfVerdict() != 0));

    }
}
