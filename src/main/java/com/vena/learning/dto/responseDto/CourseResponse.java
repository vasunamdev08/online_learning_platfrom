package com.vena.learning.dto.responseDto;

import com.vena.learning.model.Course;
import lombok.Data;

@Data
public class CourseResponse {

    private String id;
    private String description;
    private String title;
    private String instructorName;

    public CourseResponse(Course course){
        this.id=course.getId();
        this.description=course.getDescription();
        this.title=course.getTitle();
        this.instructorName= course.getInstructor().getName();
    }
}
