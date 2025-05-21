package com.vena.learning.service;

import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstructorService {
    void registerInstructor(RegisterRequest instructorRequest);
    void saveInstructor(RegisterRequest instructorRequest);
    boolean isExist(String email,String username);
    Instructor getInstructorByEmail(String email);
    Instructor getInstructorByUsername(String username);
    Instructor getInstructorById(String id);

    List<Instructor> getAllInstructorByInstitute(String institution);
}
