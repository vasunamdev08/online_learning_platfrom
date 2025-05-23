package com.vena.learning.service;

import com.vena.learning.model.Course;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface CourseService {
    List<Course> getApprovedCourses();
    Course getCourseById(String id);

    Course addCourse(Course course);
}
