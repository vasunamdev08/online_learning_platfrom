package com.vena.learning.dto.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class CreateQuizRequest {
    private String quizTitle;
    private String instructorId;
    private String courseId;
    private List<QuestionRequest> questions;
}


