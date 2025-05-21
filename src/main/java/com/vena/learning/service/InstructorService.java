package com.vena.learning.service;

import com.vena.learning.dto.CourseDTO;
import com.vena.learning.dto.CreateCourseDTO;
import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.dto.UpdateCourseDTO;
import com.vena.learning.model.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InstructorService {
    List<CourseDTO> getCoursesByInstructor(String instructorId);

    void registerInstructor(RegisterRequest instructorRequest);
    void saveInstructor(RegisterRequest instructorRequest);
    CourseDTO createCourse(CreateCourseDTO courseDTO, String instructorId);
    boolean isExist(String email,String username);
    Optional<Instructor> getInstructorByEmail(String email);
    Optional<Instructor> getInstructorByUsername(String username);
    Optional<Instructor> getInstructorById(String id);
    void updateCourse(String courseId, UpdateCourseDTO updateDTO);
}



