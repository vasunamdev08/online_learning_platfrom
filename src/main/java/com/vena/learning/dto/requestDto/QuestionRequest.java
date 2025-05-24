package com.vena.learning.dto.requestDto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionRequest {
    private String id;
    private String quizId;
    private String questionText;
    private List<ChoiceRequest> choices;
}
