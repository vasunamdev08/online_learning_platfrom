package com.vena.learning.service;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.requestDto.StudentUpdateRequest;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.model.Course;
import com.vena.learning.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StudentService {
    Student getStudentByEmail(String email);
    Student getStudentById(String id);
    Student getStudentByUsername(String username);

    boolean isExistsByEmail(String email);
    boolean isExistsByUsername(String username);
    boolean isStudentExist(String userId);
    boolean isStudentExist(String email, String username);

    void registerStudent(RegisterRequest user);
    void saveStudent(RegisterRequest user);
    void deleteStudent(String userId);

    List<Course> getStudentCources(String studentId);
    List<Student> getAllStudents();
    List<Student> getAllStudentByInstitute(String institution);

    Optional<Student> findById(String adminID);
    UserResponse updateStudentProfile(StudentUpdateRequest request);


}
