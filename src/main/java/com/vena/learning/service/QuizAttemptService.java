package com.vena.learning.service;

import com.vena.learning.dto.requestDto.QuizSubmissionRequest;
import org.springframework.stereotype.Service;

@Service
public interface QuizAttemptService {
    void submitQuiz(String studentId, String courseId, String quizId, QuizSubmissionRequest request);
}
