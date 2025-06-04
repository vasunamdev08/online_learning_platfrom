package com.vena.learning.exception.customException.CourseExceptions;

public class CourseAlreadyDeletedException extends RuntimeException {
    public CourseAlreadyDeletedException(String message) {
        super(message);
    }

    public CourseAlreadyDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseAlreadyDeletedException(Throwable cause) {
        super(cause);
    }
}
