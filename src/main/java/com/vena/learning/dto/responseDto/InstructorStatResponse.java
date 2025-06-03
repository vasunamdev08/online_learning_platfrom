package com.vena.learning.dto.responseDto;

import com.vena.learning.model.Instructor;
import lombok.Data;

import java.util.List;

@Data
public class InstructorStatResponse {
    private String id;
    private String name;
    private String email;
    private String institution;

    private List<CourseStats> courses;

    public InstructorStatResponse() {
        // Default constructor
    }

    public InstructorStatResponse(Instructor instructor){
        this.id = instructor.getId();
        this.name = instructor.getName();
        this.email = instructor.getEmail();
        this.institution = instructor.getInstitution();
        this.courses = instructor.getCourses() != null
                ? instructor.getCourses().stream()
                .map(CourseStats::new)
                .toList()
                : List.of();
    }
}
