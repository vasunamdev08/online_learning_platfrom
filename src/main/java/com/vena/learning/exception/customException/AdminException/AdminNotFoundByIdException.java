package com.vena.learning.exception.customException.AdminException;

public class AdminNotFoundByIdException extends RuntimeException{
    public AdminNotFoundByIdException(String message) {
        super(message);
    }

    public AdminNotFoundByIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminNotFoundByIdException(Throwable cause) {
        super(cause);
    }
}
