package com.vena.learning.exception.customException.StudentExceptions;

public class EnrollmentDoesNotExistException extends RuntimeException {
    public EnrollmentDoesNotExistException(String message) {
        super(message);
    }

    public EnrollmentDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnrollmentDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
