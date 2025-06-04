package com.vena.learning.exception.customException.CourseExceptions;

public class CourseTitleEmptyException extends RuntimeException{
    public CourseTitleEmptyException(String message) {
        super(message);
    }

    public CourseTitleEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseTitleEmptyException(Throwable cause) {
        super(cause);
    }
}
