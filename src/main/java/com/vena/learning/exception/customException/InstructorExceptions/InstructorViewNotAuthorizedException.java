package com.vena.learning.exception.customException.InstructorExceptions;

public class InstructorViewNotAuthorizedException extends RuntimeException {
    public InstructorViewNotAuthorizedException(String message) {
        super(message);
    }

    public InstructorViewNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructorViewNotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
