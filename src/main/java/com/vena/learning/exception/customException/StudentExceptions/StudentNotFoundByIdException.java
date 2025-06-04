package com.vena.learning.exception.customException.StudentExceptions;

public class StudentNotFoundByIdException extends RuntimeException {
    public StudentNotFoundByIdException(String message) {
        super(message);
    }

    public StudentNotFoundByIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundByIdException(Throwable cause) {
        super(cause);
    }
}
