package com.vena.learning.exception.customException.StudentExceptions;

public class StudentDetailIncompleteException extends RuntimeException {
    public StudentDetailIncompleteException(String message) {
        super(message);
    }

    public StudentDetailIncompleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentDetailIncompleteException(Throwable cause) {
        super(cause);
    }
}
