package com.vena.learning.exception.customException.ModuleExceptions;

public class FirstModuleMustBeIntroductionException extends RuntimeException {
    public FirstModuleMustBeIntroductionException(String message) {
        super(message);
    }

    public FirstModuleMustBeIntroductionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FirstModuleMustBeIntroductionException(Throwable cause) {
        super(cause);
    }
}
