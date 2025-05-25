package com.vena.learning.service;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {

    // Retrieve a course by ID and return a response DTO
    CourseResponse getCourseResponseByCourseId(String id);

    // Retrieve a course entity by ID
    Course getCourseById(String id);

    // Add a course along with its modules and return a response DTO
    CourseResponse addCourseWithModules(CourseRequest courseRequest);

    // Add a course using a request DTO and return the entity
    Course addCourse(CourseRequest courseRequest);

    // Add a course using a Course entity (for internal use)
    Course addCourse(Course course);

    // Get all approved courses
    List<CourseResponse> getApprovedCourses();

    // Get all courses by instructor ID
    List<CourseResponse> getCoursesByInstructorId(String instructorId);
}
