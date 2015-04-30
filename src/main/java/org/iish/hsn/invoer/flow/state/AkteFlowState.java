package org.iish.hsn.invoer.flow.state;

import java.io.Serializable;

/**
 * Holds the state of the 'akte' during the flow.
 */
public abstract class AkteFlowState implements Serializable {
    public static final int CORRECTION_DELETE = 999;

    private boolean isCorrection = false;
    private int correctionCode;

    private ViewStateHistory viewStateHistory;

    public boolean isCorrection() {
        return isCorrection;
    }

    public void setCorrection(boolean isCorrection) {
        this.isCorrection = isCorrection;
    }

    public int getCorrectionCode() {
        return correctionCode;
    }

    public void setCorrectionCode(int correctionCode) {
        this.correctionCode = correctionCode;
    }

    public boolean isCorrectionDelete() {
        return correctionCode == CORRECTION_DELETE;
    }

    public void setCorrectionDelete() {
        correctionCode = CORRECTION_DELETE;
    }

    public ViewStateHistory getViewStateHistory() {
        return viewStateHistory;
    }

    public void setViewStateHistory(ViewStateHistory viewStateHistory) {
        this.viewStateHistory = viewStateHistory;
    }
}
