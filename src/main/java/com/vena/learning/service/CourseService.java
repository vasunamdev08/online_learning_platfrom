package com.vena.learning.service;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.model.Course;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface CourseService {
    CourseResponse getCourseResponseByCourseId(String id);
    Course getCourseById(String id);
    CourseResponse addCourseWithModules(CourseRequest course);
    Course addCourse(CourseRequest course);

    List<CourseResponse> getApprovedCourses();
    List<CourseResponse> getCoursesByInstructorId(String instructorId);

    Course addCourse(Course course);
}