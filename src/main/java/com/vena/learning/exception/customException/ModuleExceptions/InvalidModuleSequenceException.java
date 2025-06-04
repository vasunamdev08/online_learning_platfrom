package com.vena.learning.exception.customException.ModuleExceptions;

public class InvalidModuleSequenceException extends RuntimeException {
    public InvalidModuleSequenceException(String message) {
        super(message);
    }

    public InvalidModuleSequenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidModuleSequenceException(Throwable cause) {
        super(cause);
    }
}
