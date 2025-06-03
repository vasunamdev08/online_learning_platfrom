package com.vena.learning.dto.responseDto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CourseSummaryResponse {
    private String id;
    private String title;
    private String description;
    private String instructorName;
    private List<ModuleResponse> modules;

    public CourseSummaryResponse() {
        // Default constructor
    }

    public CourseSummaryResponse(CourseResponse course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.instructorName = course.getInstructorName();
        this.modules = course.getModules() != null
                ? course.getModules().stream()
                .sorted(Comparator.comparingInt(ModuleResponse::getSequence))
                .collect(Collectors.toList())
                : new ArrayList<>();
    }
}
