package com.vena.learning.dto.responseDto;

import com.vena.learning.model.Course;
import lombok.Data;

@Data
public class InstructorsCourses {
    private String courseId;
    private String courseDescription;
    private String courseTitle;
    private boolean isApproved;

    public InstructorsCourses(Course course) {
        this.courseId = course.getId();
        this.courseDescription = course.getDescription();
        this.courseTitle = course.getTitle();
        this.isApproved = course.isApproved();
    }
    public InstructorsCourses() {
    }
}
