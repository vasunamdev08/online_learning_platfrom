package com.vena.learning.exception.customException.InstructorExceptions;

public class InstructorAlreadyExistException extends RuntimeException {
    public InstructorAlreadyExistException(String message) {
        super(message);
    }

    public InstructorAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructorAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
