package com.vena.learning.model;

import com.vena.learning.enums.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private boolean isComplete;
    private int sequence;
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Type type;
}