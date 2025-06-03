package com.vena.learning.exception.customException.StudentExceptions;

public class NoCoursesFoundForStudentException extends RuntimeException {
    public NoCoursesFoundForStudentException(String message) {
        super(message);
    }

    public NoCoursesFoundForStudentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCoursesFoundForStudentException(Throwable cause) {
        super(cause);
    }
}
