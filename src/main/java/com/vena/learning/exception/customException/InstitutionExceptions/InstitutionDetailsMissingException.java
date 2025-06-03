package com.vena.learning.exception.customException.InstitutionExceptions;

public class InstitutionDetailsMissingException extends RuntimeException{
    public InstitutionDetailsMissingException(String message) {
        super(message);
    }

    public InstitutionDetailsMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstitutionDetailsMissingException(Throwable cause) {
        super(cause);
    }
}
