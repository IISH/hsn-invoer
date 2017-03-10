package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.execution.repository.FlowExecutionRestorationFailureException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller that handles any thrown exceptions.
 */
@ControllerAdvice
public class ExceptionHandlerController implements HandlerExceptionResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    /**
     * Returns an HTTP 404 error code if the NotFoundException is thrown.
     *
     * @param nfe The thrown NotFoundException.
     * @return The message of the exception.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleNotFoundException(NotFoundException nfe) {
        return nfe.getMessage();
    }

    /**
     * Returns an HTTP 500 error code if the IOException is thrown.
     *
     * @param ioe The thrown IOException.
     * @return The message of the exception.
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody String handleIOException(IOException ioe) {
        return ioe.getMessage();
    }

    /**
     * Default exception handler.
     *
     * @param request  The request.
     * @param response The response.
     * @param handler  The handler.
     * @param ex       The exception.
     * @return The model and view to render.
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        // An ExceptionHandler for this exception won't work, so deal with it here
        if (ex instanceof FlowExecutionRestorationFailureException) {
            return new ModelAndView("exception/backButtonError");
        }

        // Make sure to log the exception
        LOGGER.error(ex.getMessage(), ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("request", request);
        mav.addObject("exception", ex);
        mav.setViewName("exception/defaultError");

        return mav;
    }
}
