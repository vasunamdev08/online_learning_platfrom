package com.vena.learning.exception.customException.InstructorExceptions;

public class InstructorDetailMissingException extends RuntimeException {
    public InstructorDetailMissingException(String message) {
        super(message);
    }

    public InstructorDetailMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructorDetailMissingException(Throwable cause) {
        super(cause);
    }
}
