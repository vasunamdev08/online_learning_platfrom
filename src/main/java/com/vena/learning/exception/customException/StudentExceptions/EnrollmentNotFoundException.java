package com.vena.learning.exception.customException.StudentExceptions;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException(String message) {
        super(message);
    }

    public EnrollmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnrollmentNotFoundException(Throwable cause) {
        super(cause);
    }
}
