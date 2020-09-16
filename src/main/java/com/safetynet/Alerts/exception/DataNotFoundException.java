package com.safetynet.Alerts.exception;

/**
 * Exception thrown when user tries to access some data that are not registered.
 */
public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(final String message) {
        super(message);
    }
}
