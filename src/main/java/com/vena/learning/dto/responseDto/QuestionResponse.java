package com.vena.learning.dto.responseDto;

import com.vena.learning.model.Question;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionResponse {
    private String id;
    private String quizId;
    private String questionText;
    private List<ChoiceResponse> choices;

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.quizId = question.getQuiz().getId();
        this.questionText = question.getQuestion();
        this.choices = question.getChoices().stream().map(ChoiceResponse::new).collect(Collectors.toList());
    }
}
