package com.vena.learning.service;

import com.vena.learning.dto.requestDto.RegisterRequest;
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
    boolean isStudentExist(String email, String username);

    void registerStudent(RegisterRequest user);
    void saveStudent(RegisterRequest user);

    List<Course> getStudentCources(String studentId);

    List<Student> getAllStudentByInstitute(String institution);

    void deleteStudent(String userId);

    Optional<Student> findById(String adminID);

    List<Student> getAllStudents();

    UserResponse updateStudentProfile(String studentId, RegisterRequest request);

}
