package com.vena.learning.dto.responseDto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CourseStatusResponse {
    Map<String, List<CourseSummaryResponse>> courseStatusMap;

    public CourseStatusResponse(Map<String, List<CourseSummaryResponse>> courseStatusMap) {
        this.courseStatusMap = courseStatusMap;
    }
}
