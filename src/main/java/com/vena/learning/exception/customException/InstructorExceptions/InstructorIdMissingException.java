package com.vena.learning.exception.customException.InstructorExceptions;

public class InstructorIdMissingException extends RuntimeException {
    public InstructorIdMissingException(String message) {
        super(message);
    }

    public InstructorIdMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructorIdMissingException(Throwable cause) {
        super(cause);
    }
}
