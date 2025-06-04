package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.requestDto.StudentUpdateRequest;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.exception.customException.CourseExceptions.CourseNotFoundByIdException;
import com.vena.learning.exception.customException.StudentExceptions.StudentAlreadyExistsException;
import com.vena.learning.exception.customException.StudentExceptions.StudentDetailIncompleteException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotFoundByEmailException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotFoundByIdException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotFoundByUsername;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotFoundException;
import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
import com.vena.learning.model.Student;
import com.vena.learning.enums.Role;
import com.vena.learning.repository.StudentRepository;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.getStudentByEmail(email).orElseThrow(
                () -> new StudentNotFoundByEmailException("Student not found with email: " + email)
        );
    }

    @Override
    public Optional<Student> findById(String adminID) {
        return studentRepository.findById(adminID);
    }

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundByIdException("Student not found with id: " + id)
        );
    }

    @Override
    public Student getStudentByUsername(String username) {
        return studentRepository.findByUsername(username).orElseThrow(
                () -> new StudentNotFoundByUsername("Student not found with username: " + username)
        );
    }

    @Override
    public boolean isExistsByUsername(String username) {
        return studentRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public boolean isStudentExist(String email, String username) {
        return isExistsByEmail(email) || isExistsByUsername(username);
    }

    @Override
    public void registerStudent(RegisterRequest user) {
        if(isStudentExist(user.getEmail(), user.getUsername())) {
            throw new StudentAlreadyExistsException("User already exists with email: " + user.getEmail() + " or username: " + user.getUsername());
        }
        if(user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new StudentDetailIncompleteException("User details are incomplete");
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
        student.setRole(Role.ROLE_STUDENT);
        studentRepository.save(student);
    }

    @Override
    public List<Course> getStudentCources(String studentId) {
        Student student = getStudentById(studentId);
        List<Course> courses = student.getEnrollments().stream().map(Enrollment::getCourse).collect(Collectors.toList());
        if(courses.isEmpty()) {
            throw new CourseNotFoundByIdException("No courses found for student with id: " + studentId);
        }
        return courses;
    }

    @Override
    public List<Student> getAllStudentByInstitute(String institution){
        return studentRepository.findByInstitution(institution).orElseThrow(()-> new StudentNotFoundException("Student not found"));
    }

    @Override
    public void deleteStudent(String userId) {
        Student student = getStudentById(userId);
        studentRepository.delete(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public UserResponse updateStudentProfile(StudentUpdateRequest request) {
        Student student = studentRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Student not found"));
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setInstitution(request.getInstitution());
        student.setPassword(request.getPassword());
        studentRepository.save(student);
        return new UserResponse(student);
    }

    @Override
    public boolean isStudentExist(String userId) {
        return studentRepository.existsById(userId);
    }
}
