package com.vena.learning.exception.customException.ModuleExceptions;

public class EmptyModulesListException extends RuntimeException {
    public EmptyModulesListException(String message) {
        super(message);
    }

    public EmptyModulesListException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyModulesListException(Throwable cause) {
        super(cause);
    }
}
