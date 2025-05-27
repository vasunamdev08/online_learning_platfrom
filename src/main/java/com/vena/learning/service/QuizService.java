package com.vena.learning.service;

import com.vena.learning.dto.requestDto.CreateQuizRequest;

public interface QuizService {
    void createQuiz(String courseId, CreateQuizRequest quizRequest);
}

