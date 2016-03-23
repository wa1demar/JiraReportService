package com.swansoftwaresolutions.jirareport.core.service.exception;

/**
 * @author Vladimir Martynyuk
 */
public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException(String message) {
        super(message);
    }
}
