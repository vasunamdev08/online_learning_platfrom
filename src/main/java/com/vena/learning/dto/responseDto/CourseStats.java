package com.vena.learning.dto.responseDto;

import com.vena.learning.model.Course;
import lombok.Data;

@Data
public class CourseStats {
    private String courseId;
    private String title;
    private boolean isApproved;
    private int totalEnrollments;
    private int completedEnrollments;
    private int noOfModules;

    public CourseStats() {
        // Default constructor
    }

    public CourseStats(Course course) {
        this.courseId = course.getId();
        this.title = course.getTitle();
        this.isApproved = course.isApproved();
        this.totalEnrollments = course.getEnrollments() != null ? course.getEnrollments().size() : 0;
        this.completedEnrollments = (int) course.getEnrollments().stream()
                .filter(enrollment -> enrollment.getIsCompleted())
                .count();
        this.noOfModules = course.getModules() != null ? course.getModules().size() : 0;
    }
}