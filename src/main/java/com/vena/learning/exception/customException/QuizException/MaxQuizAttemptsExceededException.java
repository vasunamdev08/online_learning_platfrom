package com.vena.learning.exception.customException.QuizException;

public class MaxQuizAttemptsExceededException extends RuntimeException {
    public MaxQuizAttemptsExceededException() {
        super("Maximum of 2 attempts reached. You cannot take the quiz again.");
    }
}
