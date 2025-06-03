package com.vena.learning.controller;

import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.QuizResponseWrapper;
import com.vena.learning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        List<CourseResponse> approvedCourses = courseService.getApprovedCourses();
        return ResponseEntity.ok(approvedCourses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchCourseById(@PathVariable String id) {
        CourseResponse course = courseService.getCourseResponseByCourseId(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{courseId}/quizzes")
    public ResponseEntity<?> fetchAllQuizzes(@PathVariable String courseId) {
        QuizResponseWrapper quizzes = courseService.getAllQuizzesForCourse(courseId);
        return ResponseEntity.ok(quizzes);
    }
}