package com.vena.learning.exception.customException.QuizExceptions;

public class ChoiceNotFoundException extends RuntimeException {
    public ChoiceNotFoundException(String message) {
        super(message);
    }

    public ChoiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChoiceNotFoundException(Throwable cause) {
        super(cause);
    }
}
