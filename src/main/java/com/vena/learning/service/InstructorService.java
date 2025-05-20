package com.vena.learning.service;

import com.vena.learning.dto.CourseDTO;
import com.vena.learning.dto.CreateCourseDTO;

import java.util.List;

public interface InstructorService {
    List<CourseDTO> getCoursesByInstructor(String instructorId);
    CourseDTO createCourse(CreateCourseDTO courseDTO, String instructorId);

}


