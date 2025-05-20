package com.vena.learning.service;

import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Instructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface InstructorService {
    void registerInstructor(RegisterRequest instructorRequest);
    void saveInstructor(RegisterRequest instructorRequest);
    boolean isExist(String email,String username);
    Optional<Instructor> getInstructorByEmail(String email);
    Optional<Instructor> getInstructorByUsername(String username);
    Optional<Instructor> getInstructorById(String id);
}
