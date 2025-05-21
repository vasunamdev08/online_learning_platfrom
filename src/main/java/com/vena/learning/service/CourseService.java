package com.vena.learning.service;

import com.vena.learning.model.Course;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CourseService {
    List<Course> getApprovedCourses();
    Course getCourseById(String id);
}
