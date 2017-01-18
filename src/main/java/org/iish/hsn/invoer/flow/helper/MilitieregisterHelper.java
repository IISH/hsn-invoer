package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.mil.MilitionRegistration;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict;
import org.iish.hsn.invoer.flow.state.MilitieregisterFlowState;
import org.springframework.stereotype.Component;

@Component
public class MilitieregisterHelper {
    public String hasDataOnVoogd(MilitionRegistration milReg, boolean isCorrection) {
        if (!milReg.getFirstNameGuardian().isEmpty() || !milReg.getFamilyNameGuardian().isEmpty() ||
                !milReg.getPlaceGuardian().isEmpty() || !milReg.getProfessionGuardian().isEmpty())
            return "j";
        if (isCorrection)
            return "n";
        return "";
    }

    public String hasKenmerken(MilitionRegistration milReg, boolean isCorrection) {
        if (!milReg.getFace().isEmpty() || !milReg.getForehead().isEmpty() || !milReg.getEyes().isEmpty() ||
                !milReg.getNose().isEmpty() || !milReg.getMouth().isEmpty() || !milReg.getChin().isEmpty() ||
                !milReg.getHair().isEmpty() || !milReg.getEyebrows().isEmpty() || !milReg.getNotableSigns().isEmpty())
            return "j";

        if (isCorrection)
            return "n";
        return "";
    }

    public String getVrijstellingWegensZiekte(MilitionRegistration milReg, boolean isCorrection) {
        if (milReg.getNumberRegulationIllness() > 0)
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
