package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.bev.*;
import org.iish.hsn.invoer.domain.reference.Ref_AINB;
import org.iish.hsn.invoer.domain.reference.Ref_GBH;

import java.io.Serializable;
import java.util.*;

/**
 * Holds the state of a 'bevolkingsregister' during the flow.
 */
public class BevolkingsregisterFlowState extends AkteFlowState implements Serializable {
    private Ref_GBH  refGbh;
    private Ref_AINB refAinb;

    private Registration b4;

    private List<Person>              b2;
    private List<RegistrationAddress> b6;

    private Map<Integer, List<PersonDynamic>> b3Rel;
    private Map<Integer, List<PersonDynamic>> b3Brg;
    private Map<Integer, List<PersonDynamic>> b3Kg;
    private Map<Integer, List<PersonDynamic>> b3Brp;
    private Map<Integer, List<PersonDynamic>> b3Her;
    private Map<Integer, List<PersonDynamic>> b3Ver;

    private Map<Integer, PersonDynamic> firstB3Her;
    private Map<Integer, PersonDynamic> firstB3Ver;

    private Person curB2;
    private int curB2Index = -1;

    private boolean isOneLineEach = true;
    private int     volgnrOP      = 0;
    private int     noRegels      = 0;
    private int     curPersonKey = 1;
    private int     nextPersonKey = 0;

    private RegistrationId prevRegistration = new RegistrationId();

    public BevolkingsregisterFlowState(Registration b4, List<Person> b2, List<RegistrationAddress> b6,
                                       Map<Integer, List<PersonDynamic>> b3Rel, Map<Integer, List<PersonDynamic>> b3Brg,
                                       Map<Integer, List<PersonDynamic>> b3Kg, Map<Integer, List<PersonDynamic>> b3Brp,
                                       Map<Integer, List<PersonDynamic>> b3Her, Map<Integer, List<PersonDynamic>> b3Ver,
                                       Map<Integer, PersonDynamic> firstB3Her, Map<Integer, PersonDynamic> firstB3Ver) {
        this.b4 = b4;
        this.b2 = b2;
        this.b6 = b6;
        this.b3Rel = b3Rel;
        this.b3Brg = b3Brg;
        this.b3Kg = b3Kg;
        this.b3Brp = b3Brp;
        this.b3Her = b3Her;
        this.b3Ver = b3Ver;
        this.firstB3Her = firstB3Her;
        this.firstB3Ver = firstB3Ver;
    }

    public Map<Integer, List<PersonDynamic>> getB3ForType(PersonDynamic.Type type) {
        switch (type) {
            case RELATIE_TOV_HOOFD:
                return this.b3Rel;
            case BURGELIJKE_STAND:
                return this.b3Brg;
            case KERKGENOOTSCHAP:
                return this.b3Kg;
            case BEROEP:
                return this.b3Brp;
            case HERKOMST:
                return this.b3Her;
            case VERTREK:
                return this.b3Ver;
        }
        return null;
    }

    public List<PersonDynamic> getB3ForPerson(int person) {
        List<PersonDynamic> b3 = new ArrayList<>();
        b3.addAll(this.b3Rel.get(person));
        b3.addAll(this.b3Brg.get(person));
        b3.addAll(this.b3Kg.get(person));
        b3.addAll(this.b3Brp.get(person));
        b3.addAll(this.b3Her.get(person));
        b3.addAll(this.b3Ver.get(person));
        return b3;
    }

    public Set<PersonDynamic> getAllB3() {
        Set<PersonDynamic> b3 = new HashSet<>();
        for (PersonDynamic.Type type : PersonDynamic.Type.values()) {
            for (List<PersonDynamic> personDynamics : getB3ForType(type).values()) {
                b3.addAll(personDynamics);
            }
        }
        return b3;
    }

    public Ref_GBH getRefGbh() {
        return refGbh;
    }

    public void setRefGbh(Ref_GBH refGbh) {
        this.refGbh = refGbh;
    }

    public Ref_AINB getRefAinb() {
        return refAinb;
    }

    public void setRefAinb(Ref_AINB refAinb) {
        this.refAinb = refAinb;
    }

    public Registration getB4() {
        return b4;
    }

    public void setB4(Registration b4) {
        this.b4 = b4;
    }

    public List<Person> getB2() {
        return b2;
    }

    public void setB2(List<Person> b2) {
        this.b2 = b2;
    }

    public Map<Integer, List<PersonDynamic>> getB3Rel() {
        return b3Rel;
    }

    public void setB3Rel(Map<Integer, List<PersonDynamic>> b3Rel) {
        this.b3Rel = b3Rel;
    }

    public Map<Integer, List<PersonDynamic>> getB3Brg() {
        return b3Brg;
    }

    public void setB3Brg(Map<Integer, List<PersonDynamic>> b3Brg) {
        this.b3Brg = b3Brg;
    }

    public Map<Integer, List<PersonDynamic>> getB3Kg() {
        return b3Kg;
    }

    public void setB3Kg(Map<Integer, List<PersonDynamic>> b3Kg) {
        this.b3Kg = b3Kg;
    }

    public Map<Integer, List<PersonDynamic>> getB3Brp() {
        return b3Brp;
    }

    public void setB3Brp(Map<Integer, List<PersonDynamic>> b3Brp) {
        this.b3Brp = b3Brp;
    }

    public Map<Integer, List<PersonDynamic>> getB3Her() {
        return b3Her;
    }

    public void setB3Her(Map<Integer, List<PersonDynamic>> b3Her) {
        this.b3Her = b3Her;
    }

    public Map<Integer, List<PersonDynamic>> getB3Ver() {
        return b3Ver;
    }

    public void setB3Ver(Map<Integer, List<PersonDynamic>> b3Ver) {
        this.b3Ver = b3Ver;
    }

    public List<RegistrationAddress> getB6() {
        return b6;
    }

    public void setB6(List<RegistrationAddress> b6) {
        this.b6 = b6;
    }

    public Map<Integer, PersonDynamic> getFirstB3Ver() {
        return firstB3Ver;
    }

    public void setFirstB3Ver(Map<Integer, PersonDynamic> firstB3Ver) {
        this.firstB3Ver = firstB3Ver;
    }

    public Map<Integer, PersonDynamic> getFirstB3Her() {
        return firstB3Her;
    }

    public void setFirstB3Her(Map<Integer, PersonDynamic> firstB3Her) {
        this.firstB3Her = firstB3Her;
    }

    public Person getCurB2() {
        return curB2;
    }

    public int getCurB2Index() {
        return curB2Index;
    }

    public void setCurB2Index(int curB2Index) {
        if (curB2Index < this.b2.size()) {
            this.curB2Index = curB2Index;
            this.curB2 = this.b2.get(curB2Index);
        }
    }

    public boolean isOneLineEach() {
        return isOneLineEach;
    }

    public void setOneLineEach(boolean isOneLineEach) {
        this.isOneLineEach = isOneLineEach;
    }

    public int getVolgnrOP() {
        return volgnrOP;
    }

    public void setVolgnrOP(int volgnrOP) {
        this.volgnrOP = volgnrOP;
    }

    public int getNoRegels() {
        return noRegels;
    }

    public void setNoRegels(int noRegels) {
        this.noRegels = noRegels;
    }

    public int getCurPersonKey() {
        return curPersonKey;
    }

    public void setCurPersonKey(int curPersonKey) {
        this.curPersonKey = curPersonKey;
    }

    public int getNextPersonKey() {
        return nextPersonKey;
    }

    public void setNextPersonKey(int nextPersonKey) {
        this.nextPersonKey = nextPersonKey;
    }

    public RegistrationId getPrevRegistration() {
        return prevRegistration;
    }

    public void setPrevRegistration(RegistrationId prevRegistration) {
        this.prevRegistration = prevRegistration;
    }

    public Person getOp() {
        if (this.volgnrOP <= this.b2.size()) {
            return this.b2.get(this.volgnrOP - 1);
        }
        return null;
    }
}
