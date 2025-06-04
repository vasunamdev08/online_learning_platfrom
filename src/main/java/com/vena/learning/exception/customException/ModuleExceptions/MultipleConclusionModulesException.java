package com.vena.learning.exception.customException.ModuleExceptions;

public class MultipleConclusionModulesException extends RuntimeException {
    public MultipleConclusionModulesException(String message) {
        super(message);
    }

    public MultipleConclusionModulesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipleConclusionModulesException(Throwable cause) {
        super(cause);
    }
}
