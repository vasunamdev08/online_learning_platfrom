package com.vena.learning.exception.customException.QuizException;

public class ChoiceNotFoundException extends RuntimeException {
    public ChoiceNotFoundException() {
        super("Choice not found");
    }
}
