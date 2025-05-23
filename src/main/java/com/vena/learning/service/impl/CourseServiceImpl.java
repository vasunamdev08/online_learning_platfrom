package com.vena.learning.service.impl;

import com.vena.learning.model.Course;
import com.vena.learning.repository.CourseRepository;
import com.vena.learning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course addCourse(Course course) {
        if (course.getTitle() == null || course.getDescription() == null || course.getInstructor() == null) {
            throw new RuntimeException("Course details are incomplete");
        }
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getApprovedCourses() {
        return courseRepository.getAllApprovedCourses().orElseThrow(
                () -> new RuntimeException("No approved courses found")
        );
    }

    @Override
    public Course getCourseById(String id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course not found with id: " + id)
        );
    }

}
