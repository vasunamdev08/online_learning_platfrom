package com.vena.learning.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String quizId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "question_id")
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz_attempt_id")
    private List<QuizAttempt> quizAttempts;
    private String title;
}