package com.vena.learning.exception.customException.CourseExceptions;

public class CourseAlreadyApprovedException extends RuntimeException {
    public CourseAlreadyApprovedException(String message) {
        super(message);
    }

    public CourseAlreadyApprovedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseAlreadyApprovedException(Throwable cause) {
        super(cause);
    }
}
