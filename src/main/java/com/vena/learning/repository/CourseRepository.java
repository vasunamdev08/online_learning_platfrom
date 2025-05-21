package com.vena.learning.repository;

import com.vena.learning.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findALLByInstructorId(String instructorId);
}

