package com.vena.learning.model;

import com.vena.learning.enums.Grade;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Date enrollmentDate;
    private Date completionDate;
    private Boolean isCompleted;
    private Integer progressMask;
    private Integer attempts;
    private Boolean isEnrolled;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}

