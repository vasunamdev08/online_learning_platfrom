package com.vena.learning.service;

import com.vena.learning.dto.QuestionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    List<QuestionDTO> getQuizQuestions(String courseId, String quizId);
}
