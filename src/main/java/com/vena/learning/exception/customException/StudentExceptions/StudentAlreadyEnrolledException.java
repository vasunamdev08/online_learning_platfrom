package com.vena.learning.exception.customException.StudentExceptions;

public class StudentAlreadyEnrolledException extends RuntimeException {
    public StudentAlreadyEnrolledException(String message) {
        super(message);
    }

    public StudentAlreadyEnrolledException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentAlreadyEnrolledException(Throwable cause) {
        super(cause);
    }
}
