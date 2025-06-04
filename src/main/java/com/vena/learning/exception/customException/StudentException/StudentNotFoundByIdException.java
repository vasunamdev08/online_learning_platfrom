package com.vena.learning.exception.customException.StudentException;

public class StudentNotFoundByIdException extends RuntimeException {
    public StudentNotFoundByIdException(String id) {
        super("Student not found with id: " + id);
    }
}
