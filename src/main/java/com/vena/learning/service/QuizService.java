package com.vena.learning.service;

import com.vena.learning.model.Quiz;

import com.vena.learning.dto.responseDto.QuestionResponse;
import com.vena.learning.dto.responseDto.QuizResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface QuizService {
    Quiz getQuizById(String quizId);
    List<QuestionResponse> getQuizQuestions(String studentId, String courseId, String quizId);
    List<QuizResponse> getQuizzesByCourseId(String courseId);
}
