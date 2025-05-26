package com.vena.learning.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class CourseStatusResponse {
    private List<CourseResponse> allApprovedCourses;
    private List<CourseResponse> allPendingCourses;

    public CourseStatusResponse(){}
    public CourseStatusResponse(List<CourseResponse> approved, List<CourseResponse> pending){
        this.allApprovedCourses=approved;
        this.allPendingCourses=pending;
    }
}
