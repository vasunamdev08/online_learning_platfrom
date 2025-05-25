package com.vena.learning.dto.requestDto;

import lombok.Data;

@Data
public class CreateCourseDTO {
    private String title;
    private String description;
    private String InstructorId;
}


