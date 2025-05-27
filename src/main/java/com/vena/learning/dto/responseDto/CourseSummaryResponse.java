package com.vena.learning.dto.responseDto;

import lombok.Data;
import java.util.List;

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
                ? course.getModules()
                : List.of(); // no need to map again
    }
}
