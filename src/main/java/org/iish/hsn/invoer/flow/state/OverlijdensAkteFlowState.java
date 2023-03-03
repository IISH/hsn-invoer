package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.Byz;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlagv;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlbyz;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlech;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlknd;
import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.iish.hsn.invoer.flow.helper.OverlijdensAkteViewNames;
import org.iish.hsn.invoer.flow.helper.ViewNames;

import java.io.Serializable;
import java.util.Set;

/**
 * Holds the state of a 'overlijdens akte' (death certificate) during the flow.
 */
public class OverlijdensAkteFlowState extends ByzAkteFlowState implements Serializable {
    private Ref_RP      refRp;
    private Ovlknd      ovlknd;
    private Ovlagv[]    ovlagv;
    private Ovlech[]    ovlech;
    private Set<Ovlbyz> ovlbyz;

    private Ovlagv curOvlagv;
    private int curOvlagvIndex = 0;

    private Ovlech curOvlech;
    private int curOvlechIndex = 0;

    private boolean isLevnloos           = false;
    private String  doNotCorrectAangever = "j";

    public OverlijdensAkteFlowState(Ovlknd ovlknd, Ovlagv[] ovlagv, Ovlech[] ovlech, Set<Ovlbyz> ovlbyz) {
        this.ovlknd = ovlknd;
        this.ovlagv = ovlagv;
        this.ovlech = ovlech;
        this.ovlbyz = ovlbyz;

        this.curOvlagv = this.ovlagv[this.curOvlagvIndex];
        this.curOvlech = this.ovlech[this.curOvlechIndex];
    }

    public Ref_RP getRefRp() {
        return refRp;
    }

    public void setRefRp(Ref_RP refRp) {
        this.refRp = refRp;
    }

    public Ovlknd getOvlknd() {
        return ovlknd;
    }

    public void setOvlknd(Ovlknd ovlknd) {
        this.ovlknd = ovlknd;
    }

    public Ovlagv[] getOvlagv() {
        return ovlagv;
    }

    public void setOvlagv(Ovlagv[] ovlagv) {
        this.ovlagv = ovlagv;
    }

    public Ovlagv getCurOvlagv() {
        return curOvlagv;
    }

    public int getCurOvlagvIndex() {
        return curOvlagvIndex;
    }

    public void setCurOvlagvIndex(int curOvlagvIndex) {
        if (curOvlagvIndex < this.ovlagv.length) {
            this.curOvlagvIndex = curOvlagvIndex;
            this.curOvlagv = this.ovlagv[this.curOvlagvIndex];
        }
    }

    public Ovlech[] getOvlech() {
        return ovlech;
    }

    public void setOvlech(Ovlech[] ovlech) {
        this.ovlech = ovlech;
    }

    public int getCurOvlechIndex() {
        return curOvlechIndex;
    }

    public void setCurOvlechIndex(int curOvlechIndex) {
        if (curOvlechIndex < this.ovlech.length) {
            this.curOvlechIndex = curOvlechIndex;
            this.curOvlech = this.ovlech[this.curOvlechIndex];
        }
    }

    public Ovlech getCurOvlech() {
        return curOvlech;
    }

    public Set<Ovlbyz> getOvlbyz() {
        return ovlbyz;
    }

    public void setOvlbyz(Set<Ovlbyz> ovlbyz) {
        this.ovlbyz = ovlbyz;
    }

    public void addToOvlbyz(Ovlbyz instance) {
        ovlbyz.add(instance);
    }

    public void removeFromOvlbyz(Ovlbyz instance) {
        ovlbyz.remove(instance);
    }

    public boolean isLevnloos() {
        return isLevnloos;
    }

    public void setLevnloos(boolean isLevnloos) {
        this.isLevnloos = isLevnloos;
    }

    public String getDoNotCorrectAangever() {
        return doNotCorrectAangever;
    }

    public void setDoNotCorrectAangever(String doNotCorrectAangever) {
        this.doNotCorrectAangever = doNotCorrectAangever;
    }

    @Override
    public Byz[] createNewByz() {
        Byz[] newByz = new Byz[5];
        for (int i = 0; i < 5; i++) {
            Ovlbyz byz = new Ovlbyz();
            if (i == 0) {
                ViewNames viewNames = new OverlijdensAkteViewNames(getViewStateHistory(), getCurOvlagvIndex());
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
            Ovlbyz byz = new Ovlbyz();

            // See if we have an existing byz with the correct byzNr
            for (Ovlbyz curOvlbyz : this.ovlbyz) {
                if (curOvlbyz.getByznr() == ((i + 1) + (getLastByzRound() * 5))) {
                    byz = curOvlbyz;
                }
            }

            newByz[i] = byz;
        }
        return newByz;
    }
}
