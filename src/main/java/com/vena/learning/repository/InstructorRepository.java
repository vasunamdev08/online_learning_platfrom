package com.vena.learning.repository;

import com.vena.learning.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor,String> {
    Optional<Instructor> findByUsername(String username);
    Optional<Instructor> getInstructorByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
