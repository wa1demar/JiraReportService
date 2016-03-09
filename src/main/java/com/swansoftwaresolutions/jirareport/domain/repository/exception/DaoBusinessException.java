package com.swansoftwaresolutions.jirareport.domain.repository.exception;

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