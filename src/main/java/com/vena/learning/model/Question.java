package com.vena.learning.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String questionId;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private String question;

    @OneToMany(mappedBy = "question")
    private List<Choice> choices;
}