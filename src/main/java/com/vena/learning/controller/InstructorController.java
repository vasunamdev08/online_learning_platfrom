package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.CreateQuizRequest;
import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.ModuleResponse;
import com.vena.learning.dto.responseDto.QuizResponse;
import com.vena.learning.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.PutMapping;

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

    @PutMapping("/courses")
    public ResponseEntity<?> updateCourse(@RequestBody CourseRequest request) {
        CourseResponse response = instructorService.updateInstructorCourse(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId) {
        instructorService.deleteInstructorCourse(courseId);
        return ResponseEntity.ok("Course with ID " + courseId + " has been deleted successfully.");
    }

    @PostMapping("/courses/modules")
    public ResponseEntity<CourseResponse> addModuleToCourse(@RequestBody CourseRequest courseRequest) {
        CourseResponse response = instructorService.addModuleToCourse(courseRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/courses/modules")
    public ResponseEntity<ModuleResponse> updateModule(@RequestBody ModuleRequest moduleRequest) {
        ModuleResponse updatedModule = instructorService.updateModule(moduleRequest);
        return ResponseEntity.ok(updatedModule);
    }

    @PostMapping("/courses/quizzes")
    public ResponseEntity<String> createQuizForCourse(@RequestBody CreateQuizRequest createQuizRequest) {
        instructorService.addQuizToCourse(createQuizRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz created successfully.");
    }

    @DeleteMapping("/courses/modules/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable String moduleId) {
        instructorService.deleteModule(moduleId);
        return ResponseEntity.ok("Module deleted successfully.");
    }

}
