package com.vena.learning.repository;

import com.vena.learning.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface InstructorRepository extends JpaRepository<Instructor, String> {
=======
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor,String> {
    Optional<Instructor> findByUsername(String username);
    Optional<Instructor> getInstructorByEmail(String email);

>>>>>>> cf24b522152ff3d63d2d3196a22dcd274ea557f7
}
