package com.vena.learning.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String quizId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String title;
}