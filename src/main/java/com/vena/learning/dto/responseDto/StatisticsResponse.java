package com.vena.learning.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsResponse {
    private long totalUsers;
    private long totalStudents;
    private long totalInstructors;
    private long totalCourses;
    private long approvedCourses;
    private long pendingCourses;
    private long totalEnrollments;
    private long completedEnrollments;
    private List<InstructorStats> instructors;
}