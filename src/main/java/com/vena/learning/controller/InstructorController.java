package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse (@RequestBody CourseRequest request){
        CourseResponse response = instructorService.createCourse(request);
        return ResponseEntity.ok(response);
    }
}
