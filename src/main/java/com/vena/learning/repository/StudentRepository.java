package com.vena.learning.repository;

import com.vena.learning.model.Student;
import com.vena.learning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {

    Collection<? extends User> findByInstitution(String institution);
}
