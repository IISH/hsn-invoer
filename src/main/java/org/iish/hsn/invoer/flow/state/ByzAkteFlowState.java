package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.Byz;

import java.io.Serializable;

/**
 * Holds the state of the 'akte' with byz during the flow.
 */
public abstract class ByzAkteFlowState extends AkteFlowState implements Serializable {
    private static final int MAX_LENGTH_BYZ = 55;

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

    public String getFullByz() {
        StringBuilder sb = new StringBuilder();
        for (Byz byz : byz) {
            sb.append(byz.getByz());
        }
        return sb.toString();
    }

    public void setFullByz(String fullByz) {
        for (int i = 0; i < 5; i++) {
            String textPart = fullByz.substring(0, Math.min(MAX_LENGTH_BYZ, fullByz.length()));
            fullByz = fullByz.substring(Math.min(MAX_LENGTH_BYZ, fullByz.length()));
            byz[i].setByz(textPart);
        }
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
