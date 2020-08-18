package com.safetynet.Alerts.exception;

public class DataAlreadyRegisteredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataAlreadyRegisteredException(String message) {
        super(message);
    }
}
