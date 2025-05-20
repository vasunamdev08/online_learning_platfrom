package com.vena.learning.repository;

import com.vena.learning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,String> {
    Optional<Student> getStudentByEmail(String email);
    Optional<Student> findByUsername(String username);
    Optional<Student> findById(String id);


}
