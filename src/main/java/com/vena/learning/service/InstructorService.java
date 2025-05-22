package com.vena.learning.service;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.model.Instructor;
import org.springframework.stereotype.Service;

@Service
public interface InstructorService {
    void registerInstructor(RegisterRequest instructorRequest);
    void saveInstructor(RegisterRequest instructorRequest);

    boolean isExist(String email,String username);
    boolean isExistsByEmail(String email);
    boolean isExistsByUsername(String username);

    Instructor getInstructorByEmail(String email);
    Instructor getInstructorByUsername(String username);
    Instructor getInstructorById(String id);
}
