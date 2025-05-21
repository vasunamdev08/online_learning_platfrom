package com.vena.learning.repository;

import com.vena.learning.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,String> {
    Optional<Instructor> findByUsername(String username);
    Optional<Instructor> getInstructorByEmail(String email);
    Optional<List<Instructor>> findByInstitution(String institution);
}
