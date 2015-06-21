package org.iish.hsn.invoer.flow;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.execution.repository.FlowExecutionRestorationFailureException;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles exceptions that occur through the use of the back button.
 * Redirects the user to the backButton error page,
 * which redirects the user back to the original page after a couple of seconds.
 */
public class ExceptionHandlingFlowHandler extends AbstractFlowHandler {
    @Override
    public String handleException(FlowException e, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof FlowExecutionRestorationFailureException) {
            return "contextRelative:/backButton";
        }
        return super.handleException(e, request, response);
    }
}