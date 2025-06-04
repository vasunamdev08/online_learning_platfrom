package com.vena.learning.exception.customException.StudentExceptions;

public class StudentViewNotAuthorizedException extends RuntimeException {
    public StudentViewNotAuthorizedException(String message) {
        super(message);
    }

    public StudentViewNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentViewNotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
