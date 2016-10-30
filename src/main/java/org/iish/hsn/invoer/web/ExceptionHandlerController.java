package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Controller that handles any thrown exceptions.
 */
@ControllerAdvice
public class ExceptionHandlerController {
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
     * @param req The request
     * @param e   The exception.
     * @return The modal and view to display.
     * @throws Exception The incoming exception may be rethrown to let other methods take care of it.
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let the framework handle it
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        // Make sure to log the exception
        LOGGER.error(e.getMessage(), e);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("exception/defaultError");

        return mav;
    }
}
