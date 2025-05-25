package com.vena.learning.controller;

import com.vena.learning.dto.CourseDTO;
import com.vena.learning.dto.CreateCourseDTO;
import com.vena.learning.dto.UpdateCourseDTO;
import com.vena.learning.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor/courses")
@RequiredArgsConstructor
@Validated
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getInstructorCourses(@RequestParam String instructorId) {
        List<CourseDTO> courses = instructorService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CreateCourseDTO courseDTO,
                                                  @RequestParam String instructorId) {
        CourseDTO createdCourse = instructorService.createCourse(courseDTO, instructorId);
        return ResponseEntity.ok(createdCourse);
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable String courseId,
                                               @RequestBody UpdateCourseDTO updateDTO) {
        instructorService.updateCourse(courseId, updateDTO);
        return ResponseEntity.ok("Course updated successfully");
    }
}
