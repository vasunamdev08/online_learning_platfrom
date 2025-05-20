package com.vena.learning.repository;

import com.vena.learning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // Custom queries if needed
}

