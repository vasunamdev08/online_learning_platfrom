package com.vena.learning.dto.requestDto;

import lombok.Data;

@Data
public class AnswerSubmissionRequest {
    private String questionId;
    private String selectedChoiceId;
}
