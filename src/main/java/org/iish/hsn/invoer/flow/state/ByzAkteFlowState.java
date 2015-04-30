package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.Byz;

import java.io.Serializable;

/**
 * Holds the state of the 'akte' with byz during the flow.
 */
public abstract class ByzAkteFlowState extends AkteFlowState implements Serializable {
    private Byz[] byz;
    private int lastByzRound = 0;
    private int nrOfByzEntered = 0;
    private boolean allowMoreByz = false;

    public void setCorrection(boolean isCorrection) {
        super.setCorrection(isCorrection);
        if (this.isCorrection()) {
            this.allowMoreByz = true;
        }
    }

    public Byz[] getByz() {
        return byz;
    }

    public void setByz(Byz[] byz) {
        this.byz = byz;
    }

    public int getLastByzRound() {
        return lastByzRound;
    }

    public void setLastByzRound(int lastByzRound) {
        this.lastByzRound = lastByzRound;
    }

    public int getNrOfByzEntered() {
        return nrOfByzEntered;
    }

    public void setNrOfByzEntered(int nrOfByzEntered) {
        this.nrOfByzEntered = nrOfByzEntered;
    }

    public boolean isAllowMoreByz() {
        return allowMoreByz;
    }

    public void setAllowMoreByz(boolean allowMoreByz) {
        this.allowMoreByz = allowMoreByz;
    }

    public abstract Byz[] createNewByz();

    public abstract Byz[] selectNewByzForCorrection();
}
