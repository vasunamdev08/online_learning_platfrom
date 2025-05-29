package com.vena.learning.service;


import com.vena.learning.dto.responseDto.QuestionResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface QuizService {
    List<QuestionResponse> getQuizQuestions(String studentId, String courseId, String quizId);
}
