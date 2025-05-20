package com.vena.learning.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
class Choice{

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @Column(name="is_correct")
    private boolean isCorrect;

    @Column(name="option_text")
    private String optionText;

    @ManyToOne
    @JoinColumn(name="question_id",nullable=false)
    private Question question;
}