package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private CourseService courseService;

    @PutMapping("courses/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable String courseId, @RequestBody CourseRequest request) {
        CourseResponse response = courseService.updateCourse(courseId, request);
        return ResponseEntity.ok(response);
    }
}
