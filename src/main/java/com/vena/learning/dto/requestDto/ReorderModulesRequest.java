package com.vena.learning.dto.requestDto;

import lombok.Data;
import java.util.Map;

@Data
public class ReorderModulesRequest {
    private Map<String, Integer> moduleSequenceMap; // moduleId -> new sequence
}

