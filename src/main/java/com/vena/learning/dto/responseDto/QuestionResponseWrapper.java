package com.vena.learning.dto.responseDto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionResponseWrapper {
    private List<QuestionResponse> questions;
    public QuestionResponseWrapper(List<QuestionResponse> questions) {
        this.questions = questions;
    }
}
