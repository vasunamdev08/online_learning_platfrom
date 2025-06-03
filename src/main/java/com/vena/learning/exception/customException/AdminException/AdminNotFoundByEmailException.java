package com.vena.learning.exception.customException.AdminException;

public class AdminNotFoundByEmailException extends RuntimeException{
    public AdminNotFoundByEmailException(String email) {
        super("Admin not found with email: " + email);
    }
    public AdminNotFoundByEmailException(String email, Throwable cause) {
        super("Admin not found with email: " + email, cause);
    }
    public AdminNotFoundByEmailException(String email, String message) {
        super("Admin not found with email: " + email + ". " + message);
    }
    public AdminNotFoundByEmailException(String email, String message, Throwable cause) {
        super("Admin not found with email: " + email + ". " + message, cause);
    }
}
