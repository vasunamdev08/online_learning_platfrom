package com.vena.learning.repository;

import com.vena.learning.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, String> {
}
