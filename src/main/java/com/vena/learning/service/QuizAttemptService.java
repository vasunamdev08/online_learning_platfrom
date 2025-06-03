package com.vena.learning.service;

import com.vena.learning.dto.requestDto.QuizSubmissionRequest;
import com.vena.learning.dto.responseDto.QuizAttemptResponse;
import com.vena.learning.model.Quiz;
import com.vena.learning.model.QuizAttempt;
import com.vena.learning.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizAttemptService {
    QuizAttemptResponse submitQuiz(QuizSubmissionRequest request);
    Integer calculateAttemptNumber(String studentId, String quizId);
    QuizAttempt createQuizAttempt(Student student, Quiz quiz, int attemptNumber, int score);
    int calculateScore(QuizSubmissionRequest request);
    List<QuizAttempt> findByStudentIdAndQuizId(String studentId, String quizId);
}
