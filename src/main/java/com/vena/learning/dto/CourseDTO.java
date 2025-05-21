package com.vena.learning.dto;

import com.vena.learning.model.Course;
import lombok.Data;

@Data
public class CourseDTO {
    private String id;
    private String title;
    private String description;
    private boolean isApproved;
    private boolean isDeleted;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.isApproved = course.isApproved();
        this.isDeleted = course.isDeleted();
    }
}



