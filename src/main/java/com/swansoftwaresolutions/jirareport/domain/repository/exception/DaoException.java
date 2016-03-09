package com.swansoftwaresolutions.jirareport.domain.repository.exception;

/**
 * @author Vladimir Martynyuk
 */

public class DaoException extends Exception {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}