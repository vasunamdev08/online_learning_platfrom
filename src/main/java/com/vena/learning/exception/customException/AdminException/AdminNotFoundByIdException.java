package com.vena.learning.exception.customException.AdminException;

public class AdminNotFoundByIdException extends RuntimeException{
    public AdminNotFoundByIdException(String id) {
        super("Admin not found with id: " + id);
    }

    public AdminNotFoundByIdException(String id, Throwable cause) {
        super("Admin not found with id: " + id, cause);
    }

    public AdminNotFoundByIdException(String id, String message) {
        super("Admin not found with id: " + id + ". " + message);
    }

    public AdminNotFoundByIdException(String id, String message, Throwable cause) {
        super("Admin not found with id: " + id + ". " + message, cause);
    }
}
