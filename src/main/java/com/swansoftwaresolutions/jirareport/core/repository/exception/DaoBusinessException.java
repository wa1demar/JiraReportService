package com.swansoftwaresolutions.jirareport.core.repository.exception;

/**
 * @author Vladimir Martynyuk
 */
public class DaoBusinessException extends DaoException {

    public DaoBusinessException(String message) {
        super(message);
    }

    public DaoBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}