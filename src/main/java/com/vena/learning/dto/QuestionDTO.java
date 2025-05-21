package com.vena.learning.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionDTO {
    private String id;
    private String quizId;
    private String questionText;
    private List<ChoiceDTO> choices;
}

