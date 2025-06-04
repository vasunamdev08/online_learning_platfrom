package com.vena.learning.exception.customException.ModuleExceptions;

public class ModuleNotFoundByIdAndCourseIdException extends RuntimeException {
    public ModuleNotFoundByIdAndCourseIdException(String message) {
        super(message);
    }

    public ModuleNotFoundByIdAndCourseIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModuleNotFoundByIdAndCourseIdException(Throwable cause) {
        super(cause);
    }
}
