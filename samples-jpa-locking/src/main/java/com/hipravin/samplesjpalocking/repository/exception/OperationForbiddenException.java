package com.hipravin.samplesjpalocking.repository.exception;

public class OperationForbiddenException extends OperationFailedException {
    public OperationForbiddenException() {
    }

    public OperationForbiddenException(String message) {
        super(message);
    }

    public OperationForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
