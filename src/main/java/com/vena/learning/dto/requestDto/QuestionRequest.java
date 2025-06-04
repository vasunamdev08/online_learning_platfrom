package com.vena.learning.dto.requestDto;


import lombok.Data;
import java.util.List;

@Data
public class QuestionRequest {
    private String questionText;
    private List<ChoiceRequest> choices;
}

