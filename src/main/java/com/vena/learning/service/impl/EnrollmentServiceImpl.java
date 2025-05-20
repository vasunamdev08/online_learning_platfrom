package com.vena.learning.service.impl;

import com.vena.learning.dto.EnrollmentRequestDTO;
import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
import com.vena.learning.model.Student;
import com.vena.learning.repository.CourseRepository;
import com.vena.learning.repository.EnrollmentRepository;
import com.vena.learning.repository.StudentRepository;
import com.vena.learning.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Override
    public String enrollStudent(String courseId, EnrollmentRequestDTO enrollmentRequestDTO) {
        String studentId = enrollmentRequestDTO.getStudentId();

        Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found."));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found."));

        if (enrollmentRepo.existsByStudentIdAndCourseId(studentId, courseId)) {
            return "Student is already enrolled in this course";
        }

        Enrollment enrollment = new Enrollment();
//        enrollment.setId(UUID.randomUUID().toString());
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());

        //TODO: enrollment entity is complete need to set the fields once updated,
        // missing fields -> attempts, completion_date, is_completed, progress.

        enrollmentRepo.save(enrollment);
        return "Enrolled successfully in course: " +  course.getTitle();
    }
}
