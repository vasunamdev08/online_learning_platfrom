package com.vena.learning.repository;

import com.vena.learning.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,String> {
}
