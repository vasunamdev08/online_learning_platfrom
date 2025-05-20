package com.vena.learning.repository;

import com.vena.learning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
    Optional<Student> getStudentByEmail(String email);

    Optional<Student> findByUsername(String username);

    Optional<Student> findById(String id);
}