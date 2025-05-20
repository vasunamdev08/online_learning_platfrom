package com.vena.learning.repository;

import com.vena.learning.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, String> {
}
