package com.vena.learning.dto.responseDto;

import lombok.Data;

@Data
public class CourseStats {
    private String courseId;
    private String title;
    private boolean isApproved;
    private int totalEnrollments;
    private int completedEnrollments;
    private double averageProgress;
}