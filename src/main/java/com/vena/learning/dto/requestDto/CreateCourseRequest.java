package com.vena.learning.dto.requestDto;

import lombok.Data;
import java.util.List;

@Data
public class CreateCourseRequest {
    private String title;
    private String description;
    private String instructorId;
    private List<ModuleRequest> modules;
}