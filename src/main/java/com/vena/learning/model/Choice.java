package com.vena.learning.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
class Choice{

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="choice_id")
    private String choiceId;

    @Column(name="is_correct")
    private boolean isCorrect;

    @Column(name="option_text")
    private String optionText;

    @ManyToOne
    @JoinColumn(name="question_id",nullable=false)
    private Question question;
}