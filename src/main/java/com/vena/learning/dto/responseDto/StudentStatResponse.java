package com.vena.learning.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class StudentStatResponse {
    private String id;
    private String name;
    private String email;
    private String institution;

    private List<EnrollmentSummary> enrollments;
    private List<QuizSummary> quizAttempts;
}
