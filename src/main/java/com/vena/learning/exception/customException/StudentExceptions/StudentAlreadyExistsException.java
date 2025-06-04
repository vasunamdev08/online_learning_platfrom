package com.vena.learning.exception.customException.StudentExceptions;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String message) {
        super(message);
    }

    public StudentAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
