package com.vena.learning.exception.customException.StudentException;

public class StudentNotFoundByEmailException extends RuntimeException {
    public StudentNotFoundByEmailException(String email) {
        super("Student not found with email:" + email);
    }
}
