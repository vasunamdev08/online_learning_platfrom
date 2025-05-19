package com.vena.learning.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String moduleId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private boolean isComplete;
    private String title;
    private String content;
}