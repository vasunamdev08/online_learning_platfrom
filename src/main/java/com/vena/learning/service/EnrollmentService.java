package com.vena.learning.service;

import com.vena.learning.dto.requestDto.EnrollmentRequest;
import com.vena.learning.enums.Grade;
import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
import com.vena.learning.model.Student;
import org.springframework.stereotype.Service;

@Service
public interface EnrollmentService {

    boolean isEnrolled(String studentId, String courseId);
    boolean isExists(String studentId, String courseId);

    void unenrollStudent(EnrollmentRequest enrollmentRequest);
    void enrollStudent(EnrollmentRequest enrollmentRequest);
    void addEnrollment(Student student, Course course);

    Enrollment saveEnrollment(Enrollment enrollment);
    Enrollment getCourseDetailsByIds(String studentId, String courseId);
    Grade getGradeByCourse(String studentId, String courseId);
    void setGradeBasedOnBestAttempt(String studentId, String courseId, String quizId, int attemptNumber);
}
