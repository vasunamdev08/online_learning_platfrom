package com.vena.learning.dto.responseDto;

import com.vena.learning.model.QuizAttempt;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizSummary {
    private String quizTitle;
    private Integer score;
    private LocalDateTime attemptedOn;

    public QuizSummary(QuizAttempt attempt) {
        this.quizTitle = attempt.getQuiz().getTitle();
        this.score = attempt.getScore();
        this.attemptedOn = attempt.getAttemptDate();
    }
}
