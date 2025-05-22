package com.vena.learning.service;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.model.Course;
import com.vena.learning.model.Student;
import com.vena.learning.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
}
