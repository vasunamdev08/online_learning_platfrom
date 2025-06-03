package com.vena.learning.service;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.ModuleResponse;
import com.vena.learning.model.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InstructorService {
    void registerInstructor(RegisterRequest instructorRequest);
    void saveInstructor(RegisterRequest instructorRequest);
    void deleteInstructor(String userId);
    void deleteInstructorCourse(String courseId);


    boolean isExist(String email,String username);
    boolean isExistsByEmail(String email);
    boolean isExistsByUsername(String username);

    Instructor getInstructorByEmail(String email);
    Instructor getInstructorByUsername(String username);
    Instructor getInstructorById(String id);


    Optional<Instructor> findById(String userId);

    List<Instructor> getAllInstructorByInstitute(String institution);
    List<Instructor> getAllInstructors();
    List<CourseResponse> getCoursesByInstructor(String instructorId);

    CourseResponse createCourse(CourseRequest request);
    CourseResponse updateInstructorCourse(CourseRequest request);

    CourseResponse addModuleToCourse(CourseRequest courseRequest);

    boolean isInstructorExist(String userId);
    ModuleResponse updateModule(ModuleRequest moduleRequest);
    void deleteModule(String moduleId);
}
