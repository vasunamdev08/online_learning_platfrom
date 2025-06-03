package com.vena.learning.dto.responseDto;

import com.vena.learning.model.Course;
import lombok.Data;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CourseResponse {
    private String id;
    private String title;
    private String description;
    private boolean isApproved;
    private boolean isComplete;
    private String instructorName;
    private List<ModuleResponse> modules;

    public CourseResponse(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.isApproved = course.isApproved();
        this.isComplete = course.isComplete();
        this.instructorName = course.getInstructor().getName();
        this.modules = course.getModules() != null
                ? course.getModules().stream()
                .map(ModuleResponse::new) // Convert Module to ModuleResponse
                .sorted(Comparator.comparingInt(ModuleResponse::getSequence)) // Then sort
                .collect(Collectors.toList())
                : new ArrayList<>();
    }

}

