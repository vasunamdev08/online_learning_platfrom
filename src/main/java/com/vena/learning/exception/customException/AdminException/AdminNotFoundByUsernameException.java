package com.vena.learning.exception.customException.AdminException;

public class AdminNotFoundByUsernameException extends RuntimeException {
    public AdminNotFoundByUsernameException(String message) {
        super(message);
    }

    public AdminNotFoundByUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminNotFoundByUsernameException(Throwable cause) {
        super(cause);
    }
}
