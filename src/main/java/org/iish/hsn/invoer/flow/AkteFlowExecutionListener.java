package org.iish.hsn.invoer.flow;

import org.iish.hsn.invoer.flow.state.ByzAkteFlowState;
import org.iish.hsn.invoer.flow.state.ViewStateHistory;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.definition.TransitionDefinition;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.execution.FlowExecutionListener;
import org.springframework.webflow.execution.RequestContext;

/**
 * Execution listener for all of the 'akte' flows.
 */
public class AkteFlowExecutionListener implements FlowExecutionListener {

    /**
     * Updates the view state history.
     * The view state history keep a record of the current and previous view state accessed by the user.
     * In addition, the state id of the transition to the 'next' screen
     * for both the current and previous view state is recorded as well.
     * This data is required to link the "bijzonderheden" to the screen in question
     * and to allow the user to continue with the flow after filling out the "bijzonderheden".
     *
     * @param context       The current flow request context.
     * @param previousState The previous state.
     * @param newState      The new state.
     */
    @Override
    public void stateEntered(RequestContext context, StateDefinition previousState, StateDefinition newState) {
        AttributeMap<Object> flowScope = context.getFlowScope();
        if (newState.isViewState() && flowScopeContainsAkte(flowScope)) {
            ByzAkteFlowState byzAkteFlowState = getAkteFlowState(flowScope);
            ViewStateHistory viewStateHistory = byzAkteFlowState.getViewStateHistory();
            ViewState newViewState = (ViewState) newState;

            ViewStateHistory newViewStateHistory = createViewStateHistory(newViewState, viewStateHistory);
            byzAkteFlowState.setViewStateHistory(newViewStateHistory);
        }
    }

    /**
     * In case there are multiple other flows, make sure this is an 'akte' flow
     * by checking for the 'akte' in the flow scope.
     *
     * @param flowScope The flow scope.
     *
     * @return Whether the flow scope contains an 'akte'.
     */
    private static boolean flowScopeContainsAkte(AttributeMap<Object> flowScope) {
        return (flowScope.contains("akte") && (flowScope.get("akte") instanceof ByzAkteFlowState));
    }

    /**
     * Obtain the 'akte' from the flow scope.
     *
     * @param flowScope The flow scope.
     *
     * @return The 'akte' from the flow scope.
     */
    private static ByzAkteFlowState getAkteFlowState(AttributeMap<Object> flowScope) {
        return (ByzAkteFlowState) flowScope.get("akte");
    }

    /**
     * Creates a view state history for the current view state.
     *
     * @param curState The current view state.
     * @param prevState The previous view state history, to obtain the previous view state.
     *
     * @return The view state history.
     */
    private static ViewStateHistory createViewStateHistory(ViewState curState, ViewStateHistory prevState) {
        TransitionDefinition transition = curState.getTransition("next");

        String curViewStateId = curState.getId();
        String prevViewStateId = (prevState != null) ? prevState.getCurViewStateId() : null;

        String nextStateId = (transition != null) ? transition.getTargetStateId() : null;
        String nextStateIdForPrevViewState = (prevState != null) ? prevState.getNextStateId() : null;

        // If the view state did not change, just return the original view state history
        if ((prevState == null) || !curViewStateId.equals(prevViewStateId)) {
            return new ViewStateHistory(curViewStateId, prevViewStateId, nextStateId, nextStateIdForPrevViewState);
        }
        return prevState;
    }
}
