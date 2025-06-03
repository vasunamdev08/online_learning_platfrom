package com.vena.learning.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class QuizResponseWrapper {
    private List<QuizResponse> quizzes;

    public QuizResponseWrapper(List<QuizResponse> quizzes) {
        this.quizzes = quizzes;
    }
}