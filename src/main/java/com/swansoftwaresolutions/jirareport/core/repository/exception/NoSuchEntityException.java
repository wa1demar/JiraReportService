package com.swansoftwaresolutions.jirareport.core.repository.exception;

/**
 * @author Vladimir Martynyuk
 */
public class NoSuchEntityException extends DaoBusinessException {

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
