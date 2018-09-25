package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.mil.Career;
import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict.Type;
import org.iish.hsn.invoer.domain.reference.Ref_RP;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Holds the state of a 'militieregister' during the flow.
 */
public class MilitieregisterFlowState extends AkteFlowState implements Serializable {
    private Ref_RP refRp;
    private Milition mil;

    private Map<Type, Verdict> verdict;

    private List<Career> professions;
    private List<Career> educations;

    private boolean cropSideA = true;

    private String includesExemption = "n";
    private String includesMedical = "n";
    private String includesDelay = "n";
    private String includesDelayMilition = "n";
    private String includesAppeal = "n";

    public MilitieregisterFlowState(Milition mil, Map<Type, Verdict> verdict,
                                    List<Career> professions, List<Career> educations) {
        this.mil = mil;
        this.verdict = verdict;
        this.professions = professions;
        this.educations = educations;
    }

    public boolean is(String... years) {
        return is(false, years);
    }

    public boolean isStrict(String... years) {
        return is(true, years);
    }

    private boolean is(boolean strict, String... years) {
        for (String yearAndTypes : years) {
            String year = yearAndTypes.substring(0, 4);
            boolean yearMatches = (!strict && mil.isOtherYear()) || "****".equals(year) ||
                    (mil.is1815() && "1815".equals(year)) ||
                    (mil.is1862() && "1862".equals(year)) ||
                    (mil.is1913() && "1913".equals(year)) ||
                    (mil.is1923() && "1923".equals(year));

            if (yearMatches) {
                String type = "KN".contains(mil.getType().toUpperCase()) ? "L" : mil.getType().toUpperCase();
                String types = yearAndTypes.substring(4).toUpperCase();

                if ((!strict && mil.isOtherYear()) || types.isEmpty() || types.contains(type))
                    return true;
            }
        }
        return false;
    }

    public List<Career> getCareerForType(Career.Type type) {
        switch (type) {
            case BEROEP:
                return this.professions;
            case ONDERWIJS:
                return this.educations;
        }
        return null;
    }

    public void setCareerForType(Career.Type type, List<Career> careerHistory) {
        switch (type) {
            case BEROEP:
                this.professions = careerHistory;
                break;
            case ONDERWIJS:
                this.educations = careerHistory;
                break;
        }
    }

    public boolean isCropSideA() {
        return cropSideA;
    }

    public void setCropSideA(boolean cropSideA) {
        this.cropSideA = cropSideA;
    }

    public boolean hasScan() {
        return !this.mil.getScanA().isEmpty();
    }

    public boolean hasTwoScans() {
        return !this.mil.getScanB().isEmpty();
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

    public String getIncludesMedical() {
        return includesMedical;
    }

    public void setIncludesMedical(String includesMedical) {
        this.includesMedical = includesMedical;
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

    public Verdict getVerdictMilitieraad() {
        return verdict.get(Type.MILITIERAAD);
    }

    public List<Career> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Career> professions) {
        this.professions = professions;
    }

    public List<Career> getEducations() {
        return educations;
    }

    public void setEducations(List<Career> educations) {
        this.educations = educations;
    }
}
