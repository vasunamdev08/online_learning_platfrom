package com.vena.learning.service.impl;

import com.vena.learning.model.Quiz;
import com.vena.learning.repository.QuizRepository;
import com.vena.learning.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;

public class QuizServiceImpl implements QuizService {
    @Autowired
    QuizRepository quizRepo;

    @Override
    public Quiz getQuizById(String quizId) {
        return quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
    }
}
