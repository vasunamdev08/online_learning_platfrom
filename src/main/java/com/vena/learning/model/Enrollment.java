package com.vena.learning.model;

import com.vena.learning.model.enums.Grade;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Date enrollmentDate;
    private Date completionDate;
    private Boolean isCompleted;
    private Integer progress;
    private Integer attempts;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    // Getters and Setters
}

