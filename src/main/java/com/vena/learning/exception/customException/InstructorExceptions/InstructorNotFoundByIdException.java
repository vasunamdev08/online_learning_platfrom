package com.vena.learning.exception.customException.InstructorExceptions;

public class InstructorNotFoundByIdException extends RuntimeException {
    public InstructorNotFoundByIdException(String message) {
        super(message);
    }

    public InstructorNotFoundByIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructorNotFoundByIdException(Throwable cause) {
        super(cause);
    }
}
