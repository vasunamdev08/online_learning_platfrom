package com.vena.learning.controller;

import com.vena.learning.dto.EnrollmentRequestDTO;
import com.vena.learning.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // 1. GET /student/courses – List enrolled courses

    // 2. POST /student/courses/{courseId}/enroll – Enroll in a course
    @PostMapping("courses/{courseId}/enroll")
    public ResponseEntity<String> enrollCourse(@PathVariable String courseId, @RequestBody EnrollmentRequestDTO enrollmentRequestDTO) {
        String message = enrollmentService.enrollStudent(courseId, enrollmentRequestDTO);
        return ResponseEntity.ok(message);
    }

    // 3. GET /student/courses/{courseId} – Get course details and progress
    // 4. GET /student/courses/{courseId}/modules/{moduleId} – View a specific module
    // 5. GET /student/courses/{courseId}/quizzes/{quizId} – Fetch quiz questions
    // 6. POST /student/courses/{courseId}/quizzes/{quizId}/submit – Submit quiz answers
    // 7. GET /student/courses/{courseId}/grade – View grade/score for a course
    // 8. GET /student/profile – View student profile
    // 9. PUT /student/profile – Update student profile
}
