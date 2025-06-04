package com.vena.learning.exception.customException.StudentException;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String email, String username) {
        super("User already exists with email: " + email + " or username: " + username);
    }
}
