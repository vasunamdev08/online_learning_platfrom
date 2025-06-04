package com.vena.learning.exception.customException.CourseExceptions;

public class CourseQuizAccessDeniedDueToUnapprovedStatusException extends RuntimeException{
    public CourseQuizAccessDeniedDueToUnapprovedStatusException(String message) {
        super(message);
    }

    public CourseQuizAccessDeniedDueToUnapprovedStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseQuizAccessDeniedDueToUnapprovedStatusException(Throwable cause) {
        super(cause);
    }
}
