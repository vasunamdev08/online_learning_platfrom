package com.vena.learning.exception.customException.InstructorExceptions;

public class InstructorNotFoundForCourseException extends RuntimeException {
    public InstructorNotFoundForCourseException(String message) {
        super(message);
    }

    public InstructorNotFoundForCourseException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructorNotFoundForCourseException(Throwable cause) {
        super(cause);
    }
}
