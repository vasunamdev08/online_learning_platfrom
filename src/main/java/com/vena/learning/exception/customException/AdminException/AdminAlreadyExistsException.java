package com.vena.learning.exception.customException.AdminException;

public class AdminAlreadyExistsException extends RuntimeException {
    public AdminAlreadyExistsException(String message) {
        super(message);
    }

    public AdminAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public AdminAlreadyExistsException(String message, String details) {
        super(message + ". " + details);
    }
}
