package com.vena.learning.exception.customException.StudentExceptions;

public class StudentNotFoundByEmailException extends RuntimeException {
    public StudentNotFoundByEmailException(String message) {
        super(message);
    }

    public StudentNotFoundByEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundByEmailException(Throwable cause) {
        super(cause);
    }
}
