package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.model.Instructor;
import com.vena.learning.enums.Role;
import com.vena.learning.repository.InstructorRepository;
import com.vena.learning.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Instructor getInstructorById(String id) {
        return instructorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Instructor not found with id: " + id)
        );
    }

    @Override
    public Instructor getInstructorByUsername(String username) {
        return instructorRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Instructor not found with username: " + username)
        );
    }

    @Override
    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.getInstructorByEmail(email).orElseThrow(
                () -> new RuntimeException("Instructor not found with email: " + email)
        );
    }

    @Override
    public boolean isExist(String email, String username) {
        return instructorRepository.getInstructorByEmail(email).isPresent() || instructorRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean isExistsByUsername(String username) {
        return instructorRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return instructorRepository.existsByEmail(email);
    }

    @Override
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
        if(instructorRequest.getName() == null || instructorRequest.getEmail() == null || instructorRequest.getUsername() == null || instructorRequest.getPassword() == null) {
            throw new RuntimeException("Instructor details are incomplete");
        }
        saveInstructor(instructorRequest);
    }

    @Override
    public List<Instructor> getAllInstructorByInstitute(String institution){
        return instructorRepository.findByInstitution(institution).orElseThrow(()-> new RuntimeException("Instructor not found"));
    }
    @Override
    public void deleteInstructor(String userId) {
        Instructor instructor = getInstructorById(userId);
        instructorRepository.delete(instructor);
    }

    @Override
    public Optional<Instructor> findById(String userId) {
        return instructorRepository.findById(userId);
    }

}
