package com.vena.learning.service;

import com.vena.learning.dto.requestDto.CreateCourseDTO;
import com.vena.learning.dto.requestDto.GradeUpdateRequest;
import com.vena.learning.dto.requestDto.UpdateCourseDTO;
import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.model.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InstructorService {
    List<CourseResponse> getCoursesByInstructor(String instructorId);

    void registerInstructor(RegisterRequest instructorRequest);
    void saveInstructor(RegisterRequest instructorRequest);

    CourseResponse createCourse(CreateCourseDTO courseResponse, String instructorId);
    boolean isExist(String email,String username);
    Optional<Instructor> getInstructorByEmail(String email);
    Optional<Instructor> getInstructorByUsername(String username);
    Optional<Instructor> getInstructorById(String id);
    void updateCourse(String courseId, UpdateCourseDTO updateDTO);

    List<Instructor> getAllInstructorByInstitute(String institution);
    boolean isExistsByEmail(String email);
    boolean isExistsByUsername(String username);

    void deleteInstructor(String userId);

    Optional<Instructor> findById(String userId);

    List<Instructor> getAllInstructors();

    void deleteCourse(String courseId);

    List<UserResponse> getStudentsByCourseId(String courseId);
    void updateStudentGrade(String courseId, GradeUpdateRequest gradeRequest);

}



