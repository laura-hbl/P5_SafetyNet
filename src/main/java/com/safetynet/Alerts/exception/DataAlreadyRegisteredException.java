package com.safetynet.Alerts.exception;

/**
 * Exception thrown when user tries to create already registered data.
 */
public class DataAlreadyRegisteredException extends RuntimeException {

    public DataAlreadyRegisteredException(final String message) {
        super(message);
    }
}
