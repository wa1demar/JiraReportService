package com.swansoftwaresolutions.jirareport.web.exception;

import org.springframework.validation.Errors;

/**
 * @author Vladimir Martynyuk
 */
@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException {
    private Errors errors;

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() { return errors; }
}
