package com.hipravin.samplesjpalocking.repository.exception;

public class OperationFailedException extends Exception {
    public OperationFailedException() {
        super();
    }

    public OperationFailedException(String message) {
        super(message);
    }

    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
