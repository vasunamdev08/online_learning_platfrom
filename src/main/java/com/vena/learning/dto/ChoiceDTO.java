package com.vena.learning.dto;

import lombok.Data;

@Data
public class ChoiceDTO {
    private String id;
    private String optionText;
    private boolean isCorrect;
}
