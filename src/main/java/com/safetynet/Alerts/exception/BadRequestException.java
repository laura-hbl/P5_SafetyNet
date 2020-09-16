package com.safetynet.Alerts.exception;

/**
 * Exception thrown when user makes a bad request.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(final String message) {
        super(message);
    }
}

