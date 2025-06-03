package com.vena.learning.model;

import com.vena.learning.dto.requestDto.CourseRequest;
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
    private boolean isComplete;
    private String description;
    private String title;
    private long completionMask;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @ManyToOne
    @JoinColumn(name="instructor_id")
    private Instructor instructor;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private List<Module> modules;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;

    public Course() {
    }

    public Course(CourseRequest courseRequest) {
        this.title = courseRequest.getTitle();
        this.description = courseRequest.getDescription();
        this.isApproved= false;
        this.isDeleted = false;
        this.isComplete = false;
        this.modules = courseRequest.getModules().stream()
                .map(moduleRequest -> new Module(moduleRequest, this))
                .toList();
        initializeModuleTracking();
        System.out.println("Course Completion Mask: " + completionMask);
    }

    public void initializeModuleTracking() {
        this.completionMask = (1L << modules.size()) - 1;
    }

}