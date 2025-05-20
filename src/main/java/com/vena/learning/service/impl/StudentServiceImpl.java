package com.vena.learning.service.impl;

import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Student;
import com.vena.learning.model.enums.Role;
import com.vena.learning.repository.StudentRepository;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.getStudentByEmail(email);
    }

    @Override
    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    @Override
    public Optional<Student> getStudentByUsername(String username) {
        return studentRepository.findByUsername(username);
    }

    @Override
    public boolean isStudentExist(String email, String username) {
        return getStudentByEmail(email).isPresent()|| getStudentByUsername(username).isPresent();
    }

    @Override
    public void registerStudent(RegisterRequest user) {
        if(isStudentExist(user.getEmail(), user.getUsername())) {
            throw new RuntimeException("User already exists with email: " + user.getEmail() + " or username: " + user.getUsername());
        }
        if(user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new RuntimeException("User details are incomplete");
        }
        saveStudent(user);
    }

    @Override
    public void saveStudent(RegisterRequest user) {
        Student student = new Student();
        student.setUsername(user.getUsername());
        student.setEmail(user.getEmail());
        student.setPassword(user.getPassword());
        student.setName(user.getName());
        student.setInstitution(user.getInstitution());
        student.setRole(Role.STUDENT);
        studentRepository.save(student);
    }
}
