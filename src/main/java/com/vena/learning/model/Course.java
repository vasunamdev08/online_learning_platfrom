package com.vena.learning.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
class Course{


    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="course_id")
    private String courseId;

    private boolean isApproved;
    private boolean isDeleted;
    private boolean isPublished;

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
    private List<Quiz> quizes;
}