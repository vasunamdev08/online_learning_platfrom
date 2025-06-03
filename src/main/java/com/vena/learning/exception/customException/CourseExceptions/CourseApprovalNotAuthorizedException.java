package com.vena.learning.exception.customException.CourseExceptions;

public class CourseApprovalNotAuthorizedException extends RuntimeException {
    public CourseApprovalNotAuthorizedException(String message) {
        super(message);
    }

    public CourseApprovalNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseApprovalNotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
