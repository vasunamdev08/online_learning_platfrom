package com.vena.learning.dto.requestDto;

import lombok.Data;

@Data
public class ChoiceRequest {
    private String choiceText;
    private boolean isCorrect;

}
