package com.vena.learning.exception.customException.StudentException;

public class StudentNotFoundByUsername extends RuntimeException {
    public StudentNotFoundByUsername(String username) {
        super("Student not found with username: " + username);
    }
}
