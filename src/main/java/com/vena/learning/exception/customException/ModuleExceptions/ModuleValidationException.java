package com.vena.learning.exception.customException.ModuleExceptions;

public class ModuleValidationException extends RuntimeException {
    public ModuleValidationException(String message) {
        super(message);
    }

    public ModuleValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModuleValidationException(Throwable cause) {
        super(cause);
    }
}
