package com.vena.learning.service;

import com.vena.learning.model.Quiz;
import org.springframework.stereotype.Service;

@Service
public interface QuizService {
    Quiz getQuizById(String quizId);
}
