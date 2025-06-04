package com.vena.learning.exception.customException.StudentException;

public class StudentDetailIncompleteException extends RuntimeException {
    public StudentDetailIncompleteException() {
        super("Student details are incomplete.");
    }
}
