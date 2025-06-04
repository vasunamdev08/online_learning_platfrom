package com.vena.learning.exception.customException.CourseExceptions;

public class CourseViewNotAuthorizedException extends RuntimeException {
    public CourseViewNotAuthorizedException(String message) {
        super(message);
    }

    public CourseViewNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseViewNotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
