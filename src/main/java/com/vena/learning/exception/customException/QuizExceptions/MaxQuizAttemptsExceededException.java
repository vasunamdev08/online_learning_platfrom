package com.vena.learning.exception.customException.QuizExceptions;

public class MaxQuizAttemptsExceededException extends RuntimeException {
    public MaxQuizAttemptsExceededException(String message) {
        super(message);
    }

    public MaxQuizAttemptsExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaxQuizAttemptsExceededException(Throwable cause) {
        super(cause);
    }
}
