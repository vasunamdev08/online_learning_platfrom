package com.vena.learning.service.impl;

import com.vena.learning.dto.CourseDTO;
import com.vena.learning.dto.CreateCourseDTO;
import com.vena.learning.model.Course;
import com.vena.learning.model.User;
import com.vena.learning.repository.CourseRepository;
import com.vena.learning.repository.UserRepository;
import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.enums.Role;
import com.vena.learning.repository.InstructorRepository;
import com.vena.learning.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Instructor> getInstructorById(String id) {
        return instructorRepository.findById(id);
    }

    @Override
    public Optional<Instructor> getInstructorByUsername(String username) {
        return instructorRepository.findByUsername(username);
    }

    @Override
    public Optional<Instructor> getInstructorByEmail(String email) {
        return instructorRepository.getInstructorByEmail(email);
    }

    public List<CourseDTO> getCoursesByInstructor(String instructorId) {
        List<Course> allByInstructorId = courseRepository.findALLByInstructorId(instructorId);
        // assuming such a constructor exists
        return allByInstructorId.stream()
                .map(CourseDTO::new) // assuming such a constructor exists
                .collect(Collectors.toList());
    }

    public boolean isExist(String email, String username) {
        return instructorRepository.getInstructorByEmail(email).isPresent() || instructorRepository.findByUsername(username).isPresent();
    }

    public void saveInstructor(RegisterRequest instructorRequest) {
        Instructor instructor = new Instructor();
        instructor.setName(instructorRequest.getName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setUsername(instructorRequest.getUsername());
        instructor.setPassword(instructorRequest.getPassword());
        instructor.setInstitution(instructorRequest.getInstitution());
        instructor.setRole(Role.INSTRUCTOR);
        instructorRepository.save(instructor);
    }
    @Override
    public void registerInstructor(RegisterRequest instructorRequest) {
        if(isExist(instructorRequest.getEmail(), instructorRequest.getUsername())) {
            throw new RuntimeException("Instructor already exists with email or username");
        }
        if (instructorRequest.getName() == null || instructorRequest.getEmail() == null || instructorRequest.getUsername() == null || instructorRequest.getPassword() == null) {
            throw new RuntimeException("Instructor details are incomplete");
        }
        saveInstructor(instructorRequest);
    }
}
