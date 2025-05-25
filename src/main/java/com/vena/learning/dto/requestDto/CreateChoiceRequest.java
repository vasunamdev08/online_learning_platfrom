package com.vena.learning.dto.requestDto;

import lombok.Data;

@Data
public class CreateChoiceRequest {
    private String optionText;
    private boolean isCorrect;
}
