package com.vena.learning.exception.customException.InstitutionExceptions;

public class NoCoursesFoundForInstitutionException extends RuntimeException {
    public NoCoursesFoundForInstitutionException(String message) {
        super(message);
    }

    public NoCoursesFoundForInstitutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCoursesFoundForInstitutionException(Throwable cause) {
        super(cause);
    }
}
