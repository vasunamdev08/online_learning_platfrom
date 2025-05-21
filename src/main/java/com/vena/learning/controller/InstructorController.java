package com.vena.learning.controller;

import com.vena.learning.dto.CourseDTO;
import com.vena.learning.dto.CreateCourseDTO;
import com.vena.learning.dto.UpdateCourseDTO;
import com.vena.learning.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping("/courses")
    public List<CourseDTO> getInstructorCourses(@RequestParam String instructorId) {
        return instructorService.getCoursesByInstructor(instructorId);
    }

    @PostMapping("/courses")
    public CourseDTO createCourse(@RequestBody CreateCourseDTO courseDTO,
                                  @RequestParam String instructorId) {
        return instructorService.createCourse(courseDTO, instructorId);
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable String courseId,
                                               @RequestBody UpdateCourseDTO updateDTO) {
        instructorService.updateCourse(courseId, updateDTO);
        return ResponseEntity.ok("Course updated successfully");
    }
}

