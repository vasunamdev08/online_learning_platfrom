package com.vena.learning.repository;

import com.vena.learning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
    Optional<Student> getStudentByEmail(String email);
    Optional<Student> findByUsername(String username);
    Optional<Student> findById(String id);
    Optional<List<Student>> findByInstitution(String institution);
}
