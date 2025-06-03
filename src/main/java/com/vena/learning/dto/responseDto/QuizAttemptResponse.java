package com.vena.learning.dto.responseDto;

import com.vena.learning.model.QuizAttempt;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizAttemptResponse {
    private String quizId;
    private int score;
    private int attemptNumber;
    private LocalDateTime attemptedDate;

    public QuizAttemptResponse(QuizAttempt quizAttempt) {
        this.quizId = quizAttempt.getQuiz().getId();
        this.score = quizAttempt.getScore();
        this.attemptNumber = quizAttempt.getAttemptNumber();
        this.attemptedDate = quizAttempt.getAttemptDate();
    }
}
