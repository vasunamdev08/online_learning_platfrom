package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.model.Course;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/{id}/courses")
    public ResponseEntity<?> getAllCourses(@PathVariable String id) {
        List<CourseResponse> courses = courseService.getCoursesByInstructorId(id);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody CourseRequest courseRequest) {
        CourseResponse course = courseService.addCourseWithModules(courseRequest);
        return ResponseEntity.ok(course);
    }
}