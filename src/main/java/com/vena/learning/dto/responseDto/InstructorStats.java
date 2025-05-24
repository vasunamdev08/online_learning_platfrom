package com.vena.learning.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class InstructorStats {
    private String instructorId;
    private String instructorName;
    private int totalCourses;
    private List<CourseStats> courses;
}