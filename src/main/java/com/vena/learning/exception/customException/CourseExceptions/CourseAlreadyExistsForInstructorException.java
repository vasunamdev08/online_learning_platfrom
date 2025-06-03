package com.vena.learning.exception.customException.CourseExceptions;

public class CourseAlreadyExistsForInstructorException extends RuntimeException {
    public CourseAlreadyExistsForInstructorException(String message) {
        super(message);
    }

    public CourseAlreadyExistsForInstructorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseAlreadyExistsForInstructorException(Throwable cause) {
        super(cause);
    }
}
