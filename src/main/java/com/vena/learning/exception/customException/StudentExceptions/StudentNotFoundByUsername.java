package com.vena.learning.exception.customException.StudentExceptions;

public class StudentNotFoundByUsername extends RuntimeException {
    public StudentNotFoundByUsername(String message) {
        super(message);
    }

    public StudentNotFoundByUsername(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundByUsername(Throwable cause) {
        super(cause);
    }
}
