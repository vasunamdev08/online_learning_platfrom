package com.vena.learning.dto.responseDto;

import lombok.Data;

@Data
public class QuizResponse {
    private String id;
    private String title;
    public QuizResponse(String id, String title) {
        this.id = id;
        this.title = title;
    }
}

