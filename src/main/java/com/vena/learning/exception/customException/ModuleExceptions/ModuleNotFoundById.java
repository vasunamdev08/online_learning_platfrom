package com.vena.learning.exception.customException.ModuleExceptions;

public class ModuleNotFoundById extends RuntimeException {
    public ModuleNotFoundById(String message) {
        super(message);
    }

    public ModuleNotFoundById(String message, Throwable cause) {
        super(message, cause);
    }

    public ModuleNotFoundById(Throwable cause) {
        super(cause);
    }
}
