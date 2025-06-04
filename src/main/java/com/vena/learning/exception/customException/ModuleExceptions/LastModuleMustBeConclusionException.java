package com.vena.learning.exception.customException.ModuleExceptions;

public class LastModuleMustBeConclusionException extends RuntimeException {
    public LastModuleMustBeConclusionException(String message) {
        super(message);
    }

    public LastModuleMustBeConclusionException(String message, Throwable cause) {
        super(message, cause);
    }

    public LastModuleMustBeConclusionException(Throwable cause) {
        super(cause);
    }
}
