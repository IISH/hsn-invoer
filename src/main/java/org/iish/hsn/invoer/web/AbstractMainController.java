package org.iish.hsn.invoer.web;

import org.iish.hsn.invoer.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Contains controller configuration that holds for all controllers of this application.
 */
public abstract class AbstractMainController {
    /**
     * Returns an HTTP 404 error code if the NotFoundException is thrown.
     *
     * @param nfe The thrown NotFoundException.
     * @return The message of the exception.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody String handleNotFoundException(NotFoundException nfe) {
        return nfe.getMessage();
    }
}
