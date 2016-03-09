package com.swansoftwaresolutions.jirareport.domain.repository.exception;

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
