package com.vena.learning.exception.customException.ModuleExceptions;

public class MultipleIntroductionModulesException extends RuntimeException {
    public MultipleIntroductionModulesException(String message) {
        super(message);
    }

    public MultipleIntroductionModulesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipleIntroductionModulesException(Throwable cause) {
        super(cause);
    }
}
