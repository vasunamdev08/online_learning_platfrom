package com.vena.learning.exception.customException.StudentException;

public class NoCoursesFoundForStudentException extends RuntimeException {
    public NoCoursesFoundForStudentException(String studentId) {
        super("No courses found for student with id: " + studentId);
    }
}
