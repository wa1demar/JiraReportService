package com.swansoftwaresolutions.jirareport.core.repository.exception;

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