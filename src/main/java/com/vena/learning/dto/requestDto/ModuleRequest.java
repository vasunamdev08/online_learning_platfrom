package com.vena.learning.dto.requestDto;

import com.vena.learning.enums.Type;
import lombok.Data;

@Data
public class ModuleRequest {
    private String Id;
    private String courseId;
    private int sequence;
    private String title;
    private String content;
    private Type type;
}
