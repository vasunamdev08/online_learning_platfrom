package com.vena.learning.exception.customException.InstructorExceptions;

public class InstructorNotFoundByUsernameException extends RuntimeException {
    public InstructorNotFoundByUsernameException(String message) {
        super(message);
    }

    public InstructorNotFoundByUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructorNotFoundByUsernameException(Throwable cause) {
        super(cause);
    }
}
