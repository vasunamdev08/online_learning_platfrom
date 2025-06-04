package com.vena.learning.exception.customException.StudentException;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super("Student not found");
    }
}
