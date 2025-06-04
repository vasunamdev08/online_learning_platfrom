package com.vena.learning.exception.customException.CourseExceptions;

public class CourseNotFoundByIdException extends RuntimeException{
    public CourseNotFoundByIdException(String message) {
        super(message);
    }

    public CourseNotFoundByIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseNotFoundByIdException(Throwable cause) {
        super(cause);
    }
}
