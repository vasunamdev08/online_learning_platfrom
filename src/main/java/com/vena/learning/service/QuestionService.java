package com.vena.learning.service;

import com.vena.learning.dto.requestDto.CreateChoiceRequest;
import com.vena.learning.dto.requestDto.CreateQuestionRequest;

public interface QuestionService {
    void addQuestionToQuiz(String quizId, CreateQuestionRequest questionRequest);
    void addChoiceToQuestion(String questionId, CreateChoiceRequest choiceRequest);
}
