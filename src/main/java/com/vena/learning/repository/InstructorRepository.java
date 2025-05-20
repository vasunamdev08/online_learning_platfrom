package com.vena.learning.repository;

import com.vena.learning.model.Instructor;
import com.vena.learning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,String> {

    Collection<? extends User> findByInstitution(String institution);
}
