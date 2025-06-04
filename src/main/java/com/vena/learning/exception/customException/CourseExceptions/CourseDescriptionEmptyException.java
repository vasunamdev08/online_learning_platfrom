package com.vena.learning.exception.customException.CourseExceptions;

public class CourseDescriptionEmptyException extends RuntimeException{
    public CourseDescriptionEmptyException(String message) {
        super(message);
    }

    public CourseDescriptionEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseDescriptionEmptyException(Throwable cause) {
        super(cause);
    }
}
