package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.EnrollmentRequest;
import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
import com.vena.learning.model.Student;
import com.vena.learning.enums.Grade;
import com.vena.learning.repository.EnrollmentRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    @Lazy
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public void enrollStudent(EnrollmentRequest enrollmentRequest) {
        String studentId = enrollmentRequest.getStudentId();
        String courseId = enrollmentRequest.getCourseId();
        if(isEnrolled(studentId, courseId)) {
            throw new RuntimeException("Student is already enrolled in the course");
        }
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);
        addEnrollment(student, course);
    }

    @Override
    public void addEnrollment(Student student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setIsEnrolled(true);
        enrollment.setIsCompleted(false);
        enrollment.setEnrollmentDate(new java.util.Date());
        enrollment.setAttempts(0);
        enrollment.setGrade(Grade.Pending);
        enrollment.setCompletionDate(null);
        enrollment.setProgress(0);
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void unenrollStudent(EnrollmentRequest enrollmentRequest) {
        String studentId = enrollmentRequest.getStudentId();
        String courseId = enrollmentRequest.getCourseId();
        if(!isEnrolled(studentId, courseId)) {
            throw new RuntimeException("Student is not enrolled in the course");
        }
        if(!isExists(studentId, courseId)) {
            throw new RuntimeException("Enrollment does not exist");
        }
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).get();
        enrollment.setIsEnrolled(false);
        enrollmentRepository.save(enrollment);
    }

    @Override
    public boolean isEnrolled(String studentId, String courseId) {
        return enrollmentRepository.existsByStudentIdAndCourseIdAndIsEnrolledTrue(studentId, courseId);
    }

    @Override
    public boolean isExists(String studentId, String courseId) {
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public Enrollment getCourseDetailsByIds(String studentId, String courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(
                () -> new RuntimeException("Enrollment not found with studentId: " + studentId + " and courseId: " + courseId)
        );
    }

    @Override
    public Grade getGradeByCourse(String studentId, String courseId) {
        Enrollment enrollment = getCourseDetailsByIds(studentId, courseId);
        return enrollment.getGrade();
    }
}
