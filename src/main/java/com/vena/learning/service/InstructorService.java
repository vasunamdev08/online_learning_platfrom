package com.vena.learning.service;

import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface InstructorService {
    void registerInstructor(RegisterRequest instructorRequest);
    void saveInstructor(RegisterRequest instructorRequest);
    boolean isExist(String email,String username);
    Optional<Instructor> getInstructorByEmail(String email);
    Optional<Instructor> getInstructorByUsername(String username);
    Optional<Instructor> getInstructorById(String id);

    List<Instructor> getAllInstructorByInstitute(String institution);
}
