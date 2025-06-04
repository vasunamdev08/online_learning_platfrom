package com.vena.learning.exception.customException.CourseExceptions;

public class CourseQuizAccessDeniedDueToDeletionException extends RuntimeException{
    public CourseQuizAccessDeniedDueToDeletionException(String message) {
        super(message);
    }

    public CourseQuizAccessDeniedDueToDeletionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseQuizAccessDeniedDueToDeletionException(Throwable cause) {
        super(cause);
    }
}
