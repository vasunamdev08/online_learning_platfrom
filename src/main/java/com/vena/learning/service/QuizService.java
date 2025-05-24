package com.vena.learning.service;


import com.vena.learning.dto.requestDto.QuestionRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface QuizService {
    List<QuestionRequest> getQuizQuestions(String courseId, String quizId);
}
