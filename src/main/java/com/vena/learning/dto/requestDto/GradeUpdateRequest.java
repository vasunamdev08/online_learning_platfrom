package com.vena.learning.dto.requestDto;

import com.vena.learning.enums.Grade;
import lombok.Data;

@Data
public class GradeUpdateRequest {
    private String studentId;
    private Grade grade;
}

