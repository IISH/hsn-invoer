package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.mil.MilitionRegistration;
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
    private MilitionRegistration milReg;

    private Map<Type, Verdict> verdict;

    public MilitieregisterFlowState(Milition mil, MilitionRegistration milReg, Map<Type, Verdict> verdict) {
        this.mil = mil;
        this.milReg = milReg;
        this.verdict = verdict;
    }

    public String getYear() {
        if (is1817()) return "1817";
        if (is1862()) return "1862";
        return "0";
    }

    public boolean is1817() {
        int year = mil.getMilitionId().getYear();
        return (year >= 1817) && (year <= 1861);
    }

    public boolean is1862() {
        int year = mil.getMilitionId().getYear();
        return (year >= 1862) && (year <= 1912);
    }

    public boolean isOtherYear() {
        return !is1817() && !is1862();
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

    public MilitionRegistration getMilReg() {
        return milReg;
    }

    public void setMilReg(MilitionRegistration milReg) {
        this.milReg = milReg;
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

    public Verdict getVerdictAanwijzing() {
        return verdict.get(Type.AANWIJZING);
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
