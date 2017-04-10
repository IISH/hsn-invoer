package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict;
import org.iish.hsn.invoer.flow.state.MilitieregisterFlowState;
import org.springframework.stereotype.Component;

@Component
public class MilitieregisterHelper {
    public String getTypeRegister(String type) {
        type = type.toUpperCase();
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
        return getYesOrNo(isCorrection,
                mil.getFirstNameGuardian(), mil.getFamilyNameGuardian(),
                mil.getPlaceGuardian(), mil.getProfessionGuardian());
    }

    public String hasKenmerken(Milition mil, boolean isCorrection) {
        return getYesOrNo(isCorrection,
                mil.getFace(), mil.getForehead(), mil.getEyes(), mil.getNose(), mil.getMouth(),
                mil.getChin(), mil.getHair(), mil.getEyebrows(), mil.getNotableSigns());
    }

    public String hasEerdereLichting(Milition mil, boolean isCorrection) {
        return getYesOrNo(isCorrection, mil.getFormerClass());
    }

    public String hasAanwijzingen(Milition mil, boolean isCorrection) {
        return getYesOrNo(isCorrection,
                mil.getVoluntaryService(), mil.getFormerClass(), mil.getHeadOfList(),
                mil.getOutStateService(), mil.getInGesticht());
    }

    public String hasExemption(MilitieregisterFlowState akte) {
        return getYesOrNo(akte.isCorrection(),
                akte.getMil().getReasonsExemption(), akte.getMil().getReasonsInapplicability(),
                akte.getMil().getEarlierDecisions(), akte.getMil().getAdvice(),
                checkVerdict(
                        akte.getMil().getDayOfExemptionVerdict(),
                        akte.getMil().getMonthOfExemptionVerdict(),
                        akte.getMil().getYearOfExemptionVerdict()
                ));
    }

    public String hasMedical(MilitieregisterFlowState akte) {
        return getYesOrNo(akte.isCorrection(),
                checkVerdict(
                        akte.getMil().getDayOfMedicalVerdict(),
                        akte.getMil().getMonthOfMedicalVerdict(),
                        akte.getMil().getYearOfMedicalVerdict()),
                akte.getMil().getMedicalAdvice(),
                akte.getMil().getMedicalDefects());
    }

    public String hasDelay(MilitieregisterFlowState akte) {
        return getYesOrNo(akte.isCorrection(),
                akte.getMil().getDelayOfService(), akte.getMil().getDelayInformation());
    }

    public String hasDelayMilition(MilitieregisterFlowState akte) {
        return getYesOrNo(akte.isCorrection(),
                checkVerdict(akte.getVerdictUitstel()), checkVerdict(akte.getVerdictTweedeUitstel()));
    }

    public String hasAppeal(MilitieregisterFlowState akte) {
        return getYesOrNo(akte.isCorrection(),
                checkVerdict(akte.getVerdictBezwaren()), checkVerdict(akte.getVerdictWet()),
                checkVerdict(akte.getVerdictKoning()));
    }

    public String hasVrijgesteld(MilitieregisterFlowState akte) {
        return getYesOrNo(akte.isCorrection(), akte.getMil().getMilitionChairImprovements());
    }

    public String hasUitgesteld(MilitieregisterFlowState akte) {
        return getYesOrNo(akte.isCorrection(),
                checkVerdict(akte.getVerdictUitstel()), checkVerdict(akte.getVerdictTweedeUitstel()));
    }

    private String checkVerdict(Verdict verdict) {
        if ((verdict != null) && ((verdict.getDayOfVerdict() != 0) ||
                (verdict.getMonthOfVerdict() != 0) || (verdict.getYearOfVerdict() != 0)))
            return "j";
        return "";
    }

    private String checkVerdict(int day, int month, int year) {
        if ((day != 0) || (month != 0) || (year != 0))
            return "j";
        return "";
    }

    private String getYesOrNo(boolean isCorrection, String... values) {
        boolean isEntered = false;
        for (String value : values) {
            if (!value.isEmpty())
                isEntered = true;
        }

        if (isEntered)
            return "j";
        if (isCorrection)
            return "n";
        return "";
    }
}
