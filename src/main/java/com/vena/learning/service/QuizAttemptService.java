package com.vena.learning.service;

import com.vena.learning.dto.requestDto.QuizSubmissionRequest;
import com.vena.learning.model.Quiz;
import com.vena.learning.model.QuizAttempt;
import com.vena.learning.model.Student;
import org.springframework.stereotype.Service;

@Service
public interface QuizAttemptService {
    void submitQuiz(String studentId, String courseId, String quizId, QuizSubmissionRequest request);
    int calculateNewAttemptNumber(String studentId, String quizId);
    QuizAttempt createQuizAttempt(Student student, Quiz quiz, int attemptNumber, int score);
    int calculateScore(QuizSubmissionRequest request);
}
