package org.iish.hsn.invoer.flow.state;

import java.io.Serializable;

/**
 * The view state history keep a record of the current and previous view state accessed by the user.
 * In addition, the state id of the transition to the 'next' screen
 * for both the current and previous view state is recorded as well.
 * This data is required to link the "bijzonderheden" to the screen in question
 * and to allow the user to continue with the flow after filling out the "bijzonderheden".
 */
public class ViewStateHistory implements Serializable {
    String curViewStateId;
    String prevViewStateId;

    String nextStateId;
    String nextStateIdForPrevViewState;

    public ViewStateHistory(String curViewStateId, String nextStateId) {
        this.curViewStateId = curViewStateId;
        this.nextStateId = nextStateId;
    }

    public ViewStateHistory(String curViewStateId, String prevViewStateId, String nextStateId, String nextStateIdForPrevViewState) {
        this.curViewStateId = curViewStateId;
        this.prevViewStateId = prevViewStateId;
        this.nextStateId = nextStateId;
        this.nextStateIdForPrevViewState = nextStateIdForPrevViewState;
    }

    public String getCurViewStateId() {
        return curViewStateId;
    }

    public String getPrevViewStateId() {
        return prevViewStateId;
    }

    public String getNextStateId() {
        return nextStateId;
    }

    public String getNextStateIdForPrevViewState() {
        return nextStateIdForPrevViewState;
    }
}
