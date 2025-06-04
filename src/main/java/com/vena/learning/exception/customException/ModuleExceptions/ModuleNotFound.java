package com.vena.learning.exception.customException.ModuleExceptions;

public class ModuleNotFound extends RuntimeException {
    public ModuleNotFound(String message) {
        super(message);
    }

    public ModuleNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ModuleNotFound(Throwable cause) {
        super(cause);
    }
}
