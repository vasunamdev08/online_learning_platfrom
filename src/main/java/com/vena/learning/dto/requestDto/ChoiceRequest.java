package com.vena.learning.dto.requestDto;

import lombok.Data;

@Data
public class ChoiceRequest {
    private String id;
    private String optionText;
    private boolean isCorrect;
}
