package com.vena.learning.controller;

import com.vena.learning.dto.CourseDTO;
import com.vena.learning.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getInstructorCourses(Principal principal) {
        String instructorId = principal.getName(); // assuming JWT or session-based auth
        List<CourseDTO> courses = instructorService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/courses")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CreateCourseDTO courseDTO, Principal principal) {
        String instructorId = principal.getName();
        CourseDTO createdCourse = instructorService.createCourse(courseDTO, instructorId);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

}

