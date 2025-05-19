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

    @Column(name="is_approved")
    private boolean isApproved;

    private String description;
    private String title;

    @OneToMany(mappedBy="enrollment_id")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name="instructor_id")
    private Instructor instructor;

    @OneToMany(mappedBy="module_id")
    private Module module;

    @OneToMany(mappedBy="quiz_id")
    private Quiz quiz;
}