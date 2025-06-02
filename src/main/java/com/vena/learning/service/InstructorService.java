package com.vena.learning.service;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.model.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InstructorService {
    void registerInstructor(RegisterRequest instructorRequest);
    void saveInstructor(RegisterRequest instructorRequest);

    boolean isExist(String email,String username);
    Instructor getInstructorByEmail(String email);
    Instructor getInstructorByUsername(String username);
    Instructor getInstructorById(String id);

    List<Instructor> getAllInstructorByInstitute(String institution);
    boolean isExistsByEmail(String email);
    boolean isExistsByUsername(String username);

    void deleteInstructor(String userId);

    List<Instructor> getAllInstructors();
    List<CourseResponse> getCoursesByInstructor(String instructorId);

    CourseResponse createCourse(CourseRequest request);
    CourseResponse updateInstructorCourse(CourseRequest request);

    void deleteInstructorCourse(String courseId);

    boolean isInstructorExist(String userId);
}
