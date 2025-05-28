package com.vena.learning.controller;

import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.vena.learning.dto.requestDto.CourseRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping("/{instructorId}/courses")
    public ResponseEntity<List<CourseResponse>> getInstructorCourses(@PathVariable String instructorId) {
        List<CourseResponse> courses = instructorService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse (@RequestBody CourseRequest request){
        CourseResponse response = instructorService.createCourse(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId) {
        instructorService.deleteInstructorCourse(courseId);
        return ResponseEntity.ok("Course with ID " + courseId + " has been deleted successfully.");
    }

}
