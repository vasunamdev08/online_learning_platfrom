package com.vena.learning.exception.customException.CourseExceptions;

public class ApprovedCourseNotFoundException extends RuntimeException {
    public ApprovedCourseNotFoundException(String message) {
        super(message);
    }

    public ApprovedCourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApprovedCourseNotFoundException(Throwable cause) {
        super(cause);
    }
}
