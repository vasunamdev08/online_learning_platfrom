package com.vena.learning.exception.customException.InstructorExceptions;

public class InstructorCourseOwnershipException extends RuntimeException {
    public InstructorCourseOwnershipException(String message) {
        super(message);
    }

    public InstructorCourseOwnershipException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructorCourseOwnershipException(Throwable cause) {
        super(cause);
    }
}
