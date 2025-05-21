package com.vena.learning.model;

import jakarta.persistence.CascadeType;
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
public class Course{


    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;
    private boolean isApproved;
    private boolean isDeleted;
    private String description;
    private String title;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @ManyToOne
    @JoinColumn(name="instructor_id")
    private Instructor instructor;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private List<Module> modules;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;
}