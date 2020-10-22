package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.Byz;
import org.iish.hsn.invoer.domain.invoer.geb.*;
import org.iish.hsn.invoer.flow.helper.GeboorteAkteViewNames;
import org.iish.hsn.invoer.flow.helper.ViewNames;

import java.io.Serializable;
import java.util.Set;

/**
 * Holds the state of a 'geboorte akte' (birth certificate) during the flow.
 */
public class GeboorteAkteFlowState extends ByzAkteFlowState implements Serializable {
    private Stpb        stpb;
    private Gebakte     gebakte;
    private Gebknd      gebknd;
    private Gebvdr      gebvdr;
    private Gebkant     gebkant;
    private Gebgtg[]    gebgtg;
    private Set<Gebbyz> gebbyz;

    private Gebgtg curGebgtg;
    private int curGebgtgIndex = 0;

    public GeboorteAkteFlowState(Gebakte gebakte, Gebknd gebknd, Gebvdr gebvdr, Gebkant gebkant, Gebgtg[] gebgtg, Set<Gebbyz> gebbyz) {
        this.gebakte = gebakte;
        this.gebknd = gebknd;
        this.gebvdr = gebvdr;
        this.gebkant = gebkant;
        this.gebgtg = gebgtg;
        this.gebbyz = gebbyz;

        this.curGebgtg = gebgtg[this.curGebgtgIndex];
    }

    public Stpb getStpb() {
        return stpb;
    }

    public void setStpb(Stpb stpb) {
        this.stpb = stpb;
    }

    public Gebakte getGebakte() {
        return gebakte;
    }

    public void setGebakte(Gebakte gebakte) {
        this.gebakte = gebakte;
    }

    public Gebknd getGebknd() {
        return gebknd;
    }

    public void setGebknd(Gebknd gebknd) {
        this.gebknd = gebknd;
    }

    public Gebvdr getGebvdr() {
        return gebvdr;
    }

    public void setGebvdr(Gebvdr gebvdr) {
        this.gebvdr = gebvdr;
    }

    public Gebkant getGebkant() {
        return gebkant;
    }

    public void setGebkant(Gebkant gebkant) {
        this.gebkant = gebkant;
    }

    public Gebgtg[] getGebgtg() {
        return gebgtg;
    }

    public void setGebgtg(Gebgtg[] gebgtg) {
        this.gebgtg = gebgtg;
    }

    public Gebgtg getCurGebgtg() {
        return curGebgtg;
    }

    public int getCurGebgtgIndex() {
        return curGebgtgIndex;
    }

    public void setCurGebgtgIndex(int curGebgtgIndex) {
        if (curGebgtgIndex < this.gebgtg.length) {
            this.curGebgtgIndex = curGebgtgIndex;
            this.curGebgtg = this.gebgtg[this.curGebgtgIndex];
        }
    }

    public Set<Gebbyz> getGebbyz() {
        return gebbyz;
    }

    public void setGebbyz(Set<Gebbyz> gebbyz) {
        this.gebbyz = gebbyz;
    }

    public void addToGebbyz(Gebbyz instance) {
        gebbyz.add(instance);
    }

    public void removeFromGebbyz(Gebbyz instance) {
        gebbyz.remove(instance);
    }

    @Override
    public Byz[] createNewByz() {
        Byz[] newByz = new Byz[5];
        for (int i = 0; i < 5; i++) {
            Gebbyz byz = new Gebbyz();
            if (i == 0) {
                ViewNames viewNames = new GeboorteAkteViewNames(getViewStateHistory(), getCurGebgtgIndex());
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
            Gebbyz byz = new Gebbyz();

            // See if we have an existing byz with the correct byzNr
            for (Gebbyz curGebbyz : this.gebbyz) {
                if (curGebbyz.getByznr() == ((i + 1) + (getLastByzRound() * 5))) {
                    byz = curGebbyz;
                }
            }

            newByz[i] = byz;
        }
        return newByz;
    }
}
