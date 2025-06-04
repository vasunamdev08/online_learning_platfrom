package com.vena.learning.exception.customException.QuizExceptions;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String message) {
        super(message);
    }

    public QuizNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuizNotFoundException(Throwable cause) {
        super(cause);
    }
}
