package com.vena.learning.exception.customException.InstructorExceptions;

public class InstructorNotFoundByEmailException extends RuntimeException {
    public InstructorNotFoundByEmailException(String message) {
        super(message);
    }

    public InstructorNotFoundByEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructorNotFoundByEmailException(Throwable cause) {
        super(cause);
    }
}
