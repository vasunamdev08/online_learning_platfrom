package com.vena.learning.service;

import com.vena.learning.dto.EnrollmentRequestDto;
import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
import com.vena.learning.model.Student;
import org.springframework.stereotype.Service;

@Service
public interface EnrollmentService {

    boolean isEnrolled(String studentId, String courseId);
    boolean isExists(String studentId, String courseId);

    void unenrollStudent(EnrollmentRequestDto enrollmentRequestDto);
    void enrollStudent(EnrollmentRequestDto enrollmentRequestDto);
    void addEnrollment(Student student, Course course);

    Enrollment getCourseDetailsByIds(String studentId, String courseId);
}
