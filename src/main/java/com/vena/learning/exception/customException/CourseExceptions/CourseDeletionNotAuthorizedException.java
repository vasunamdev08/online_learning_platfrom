package com.vena.learning.exception.customException.CourseExceptions;

public class CourseDeletionNotAuthorizedException extends RuntimeException {
    public CourseDeletionNotAuthorizedException(String message) {
        super(message);
    }

    public CourseDeletionNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseDeletionNotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
