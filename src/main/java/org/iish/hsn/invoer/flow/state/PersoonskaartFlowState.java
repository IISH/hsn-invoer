package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.Byz;
import org.iish.hsn.invoer.domain.invoer.pk.*;
import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.iish.hsn.invoer.flow.helper.PersoonskaartViewNames;
import org.iish.hsn.invoer.flow.helper.ViewNames;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Holds the state of a 'persoonskaart' (person card) during the flow.
 */
public class PersoonskaartFlowState extends ByzAkteFlowState implements Serializable {
    private Ref_RP refRp;
    private Pkknd  pkknd;
    private P7     p7;
    private P8     p8Origin;
    private P8     p8CurWhereabouts;

    private List<Pkbrp>    pkbrp;
    private List<Pkhuw>    pkhuw;
    private List<Pkadres>  pkadres;
    private List<Pkeigknd> pkeigknd;

    private Set<Pkbyz> pkbyz;

    private Pkhuw curPkhuw;
    private int curPkhuwIndex = -1;

    private Pkeigknd curPkeigknd;
    private int curPkeigkndIndex = -1;

    private String anotherMarriage = "j";
    private String addAddresses    = "j";
    private String anotherChild    = "j";

    public PersoonskaartFlowState(Pkknd pkknd, P7 p7, P8 p8Origin, P8 p8CurWhereabouts, List<Pkbrp> pkbrp,
                                  List<Pkhuw> pkhuw, List<Pkadres> pkadres, List<Pkeigknd> pkeigknd, Set<Pkbyz> pkbyz) {
        this.pkknd = pkknd;
        this.p7 = p7;
        this.p8Origin = p8Origin;
        this.p8CurWhereabouts = p8CurWhereabouts;
        this.pkbrp = pkbrp;
        this.pkhuw = pkhuw;
        this.pkadres = pkadres;
        this.pkeigknd = pkeigknd;
        this.pkbyz = pkbyz;
    }

    /*public Ref_GBH getRefGbh() {
        return refGbh;
    }

    public void setRefGbh(Ref_GBH refGbh) {
        this.refGbh = refGbh;
    }*/

    public Ref_RP getRefRp() {
        return refRp;
    }

    public void setRefRp(Ref_RP refRp) {
        this.refRp = refRp;
    }

    public Pkknd getPkknd() {
        return pkknd;
    }

    public void setPkknd(Pkknd pkknd) {
        this.pkknd = pkknd;
    }

    public P7 getP7() {
        return p7;
    }

    public void setP7(P7 p7) {
        this.p7 = p7;
    }

    public P8 getP8Origin() {
        return p8Origin;
    }

    public void setP8Origin(P8 p8Origin) {
        this.p8Origin = p8Origin;
    }

    public P8 getP8CurWhereabouts() {
        return p8CurWhereabouts;
    }

    public void setP8CurWhereabouts(P8 p8CurWhereabouts) {
        this.p8CurWhereabouts = p8CurWhereabouts;
    }

    public List<Pkbrp> getPkbrp() {
        return pkbrp;
    }

    public void setPkbrp(List<Pkbrp> pkbrp) {
        this.pkbrp = pkbrp;
    }

    public Set<Pkbyz> getPkbyz() {
        return pkbyz;
    }

    public List<Pkhuw> getPkhuw() {
        return pkhuw;
    }

    public void setPkhuw(List<Pkhuw> pkhuw) {
        this.pkhuw = pkhuw;
    }

    public Pkhuw getCurPkhuw() {
        return curPkhuw;
    }

    public int getCurPkhuwIndex() {
        return curPkhuwIndex;
    }

    public void setCurPkhuwIndex(int curPkhuwIndex) {
        if (curPkhuwIndex < this.pkhuw.size()) {
            this.curPkhuwIndex = curPkhuwIndex;
            this.curPkhuw = this.pkhuw.get(curPkhuwIndex);
        }
    }

    public List<Pkadres> getPkadres() {
        return pkadres;
    }

    public void setPkadres(List<Pkadres> pkadres) {
        this.pkadres = pkadres;
    }

    public List<Pkeigknd> getPkeigknd() {
        return pkeigknd;
    }

    public void setPkeigknd(List<Pkeigknd> pkeigknd) {
        this.pkeigknd = pkeigknd;
    }

    public Pkeigknd getCurPkeigknd() {
        return curPkeigknd;
    }

    public int getCurPkeigkndIndex() {
        return curPkeigkndIndex;
    }

    public void setCurPkeigkndIndex(int curPkeigkndIndex) {
        if (curPkeigkndIndex < this.pkeigknd.size()) {
            this.curPkeigkndIndex = curPkeigkndIndex;
            this.curPkeigknd = this.pkeigknd.get(curPkeigkndIndex);
        }
    }

    public void setPkbyz(Set<Pkbyz> pkbyz) {
        this.pkbyz = pkbyz;
    }

    public void addToPkbyz(Pkbyz instance) {
        pkbyz.add(instance);
    }

    public void removeFromPkbyz(Pkbyz instance) {
        pkbyz.remove(instance);
    }

    public String getAnotherMarriage() {
        return anotherMarriage;
    }

    public void setAnotherMarriage(String anotherMarriage) {
        this.anotherMarriage = anotherMarriage;
    }

    public String getAddAddresses() {
        return addAddresses;
    }

    public void setAddAddresses(String addAddresses) {
        this.addAddresses = addAddresses;
    }

    public String getAnotherChild() {
        return anotherChild;
    }

    public void setAnotherChild(String anotherChild) {
        this.anotherChild = anotherChild;
    }

    @Override
    public Byz[] createNewByz() {
        Byz[] newByz = new Byz[5];
        for (int i = 0; i < 5; i++) {
            Pkbyz byz = new Pkbyz();
            if (i == 0) {
                ViewNames viewNames = new PersoonskaartViewNames(getViewStateHistory());
                byz.setByz(viewNames.getPrevByzViewName() + ": ");
            }
            newByz[i] = byz;
        }
        return newByz;
    }

    @Override
    public Byz[] selectNewByzForCorrection() {
        Byz[] newByz = new Byz[5];
        for (int i = 0; i < 5; i++) {
            Pkbyz byz = new Pkbyz();

            // See if we have an existing byz with the correct byzNr
            for (Pkbyz curPkbyz : this.pkbyz) {
                if (curPkbyz.getByznr() == ((i + 1) + (getLastByzRound() * 5))) {
                    byz = curPkbyz;
                }
            }

            newByz[i] = byz;
        }
        return newByz;
    }
}
