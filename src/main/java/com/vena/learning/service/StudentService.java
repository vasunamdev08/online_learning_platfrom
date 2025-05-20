package com.vena.learning.service;

import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Student;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public interface StudentService {
    Optional<Student> getStudentByEmail(String email);
    Optional<Student> getStudentById(String id);
    Optional<Student> getStudentByUsername(String username);
    boolean isStudentExist(String email, String username);
    void registerStudent(RegisterRequest user);
    void saveStudent(RegisterRequest user);
}
