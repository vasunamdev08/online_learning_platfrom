package com.vena.learning.dto;

import com.vena.learning.model.Course;

public class CourseDTO {
    private String id;
    private String title;
    private String description;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
    }

    // Getters and setters
}

