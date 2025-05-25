package com.vena.learning.service;

import com.vena.learning.dto.CourseDTO;
import com.vena.learning.dto.CreateCourseDTO;
import com.vena.learning.dto.UpdateCourseDTO;
import com.vena.learning.dto.requestDto.RegisterRequest;
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
    Instructor getInstructorByEmail(String email);
    Instructor getInstructorByUsername(String username);
    Instructor getInstructorById(String id);

    List<Instructor> getAllInstructorByInstitute(String institution);
    boolean isExistsByEmail(String email);
    boolean isExistsByUsername(String username);

    void deleteInstructor(String userId);

    Optional<Instructor> findById(String userId);


    List<Instructor> getAllInstructors();
}



