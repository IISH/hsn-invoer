package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict.Type;
import org.iish.hsn.invoer.domain.reference.Ref_RP;

import java.io.Serializable;
import java.util.Map;

/**
 * Holds the state of a 'militieregister' during the flow.
 */
public class MilitieregisterFlowState extends AkteFlowState implements Serializable {
    private Ref_RP refRp;
    private Milition mil;

    private Map<Type, Verdict> verdict;

    private boolean cropSideA = true;

    private String includesExemption = "j";
    private String includesDelay = "j";
    private String includesDelayMilition = "j";
    private String includesAppeal = "j";

    public MilitieregisterFlowState(Milition mil, Map<Type, Verdict> verdict) {
        this.mil = mil;
        this.verdict = verdict;
    }

    public String getYear() {
        if (is1815()) return "1815";
        if (is1862()) return "1862";
        if (is1913()) return "1913";
        return "0";
    }

    public boolean is1815() {
        int year = mil.getYear();
        return (year >= 1815) && (year <= 1861);
    }

    public boolean is1862() {
        int year = mil.getYear();
        return (year >= 1862) && (year <= 1912);
    }

    public boolean is1913() {
        int year = mil.getYear();
        return (year >= 1913) && (year <= 1922);
    }

    public boolean isOtherYear() {
        return !is1815() && !is1862() && !is1913();
    }

    public boolean isCropSideA() {
        return cropSideA;
    }

    public void setCropSideA(boolean cropSideA) {
        this.cropSideA = cropSideA;
    }

    public Ref_RP getRefRp() {
        return refRp;
    }

    public void setRefRp(Ref_RP refRp) {
        this.refRp = refRp;
    }

    public Milition getMil() {
        return mil;
    }

    public void setMil(Milition mil) {
        this.mil = mil;
    }

    public String getIncludesExemption() {
        return includesExemption;
    }

    public void setIncludesExemption(String includesExemption) {
        this.includesExemption = includesExemption;
    }

    public String getIncludesDelay() {
        return includesDelay;
    }

    public void setIncludesDelay(String includesDelay) {
        this.includesDelay = includesDelay;
    }

    public String getIncludesDelayMilition() {
        return includesDelayMilition;
    }

    public void setIncludesDelayMilition(String includesDelayMilition) {
        this.includesDelayMilition = includesDelayMilition;
    }

    public String getIncludesAppeal() {
        return includesAppeal;
    }

    public void setIncludesAppeal(String includesAppeal) {
        this.includesAppeal = includesAppeal;
    }

    public Map<Type, Verdict> getVerdict() {
        return verdict;
    }

    public void setVerdict(Map<Type, Verdict> verdict) {
        this.verdict = verdict;
    }

    public Verdict getVerdictUitstel() {
        return verdict.get(Type.UITSTEL);
    }

    public Verdict getVerdictTweedeUitstel() {
        return verdict.get(Type.TWEEDE_UITSTEL);
    }

    public Verdict getVerdictBezwaren() {
        return verdict.get(Type.BEZWAREN);
    }

    public Verdict getVerdictWet() {
        return verdict.get(Type.WET);
    }

    public Verdict getVerdictKoning() {
        return verdict.get(Type.KONING);
    }
}
