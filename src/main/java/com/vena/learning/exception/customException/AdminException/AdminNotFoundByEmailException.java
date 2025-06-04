package com.vena.learning.exception.customException.AdminException;

public class AdminNotFoundByEmailException extends RuntimeException{
    public AdminNotFoundByEmailException(String message) {
        super(message);
    }

    public AdminNotFoundByEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminNotFoundByEmailException(Throwable cause) {
        super(cause);
    }
}
