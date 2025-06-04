package com.vena.learning.exception.customException.StudentException;

public class StudentNotEnrolledInCourseException extends RuntimeException {
    public StudentNotEnrolledInCourseException(String studentId, String courseId) {
        super("Student with id " + studentId + " is not enrolled in the course " + courseId);
    }
}
