package com.vena.learning.exception.customException.StudentExceptions;

public class StudentNotEnrolledInCourseException extends RuntimeException {
    public StudentNotEnrolledInCourseException(String message) {
        super(message);
    }

    public StudentNotEnrolledInCourseException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotEnrolledInCourseException(Throwable cause) {
        super(cause);
    }
}
