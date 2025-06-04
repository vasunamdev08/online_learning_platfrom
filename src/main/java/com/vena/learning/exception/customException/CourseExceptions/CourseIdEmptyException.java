package com.vena.learning.exception.customException.CourseExceptions;

public class CourseIdEmptyException extends RuntimeException {
    public CourseIdEmptyException(String message) {
        super(message);
    }

    public CourseIdEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseIdEmptyException(Throwable cause) {
        super(cause);
    }
}
