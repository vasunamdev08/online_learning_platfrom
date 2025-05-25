package com.vena.learning.service;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.InstructorsCourses;
import com.vena.learning.model.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InstructorService {
    void registerInstructor(RegisterRequest instructorRequest);
    void saveInstructor(RegisterRequest instructorRequest);

    Instructor getInstructorByEmail(String email);
    Instructor getInstructorByUsername(String username);
    Instructor getInstructorById(String id);

    boolean isExist(String email,String username);
    boolean isExistsByEmail(String email);
    boolean isExistsByUsername(String username);

    List<Instructor> getAllInstructorByInstitute(String institution);

    void deleteInstructor(String userId);

    Optional<Instructor> findById(String userId);


    List<Instructor> getAllInstructors();

}
