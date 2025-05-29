package com.vena.learning.dto.requestDto;

import lombok.Data;
import java.util.List;

@Data
public class QuizSubmissionRequest {
    private String studentId;
    private List<AnswerSubmissionRequest> answers;
}
