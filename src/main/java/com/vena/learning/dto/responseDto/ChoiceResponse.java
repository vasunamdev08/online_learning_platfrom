package com.vena.learning.dto.responseDto;

import com.vena.learning.model.Choice;
import lombok.Data;

@Data
public class ChoiceResponse {
    private String id;
    private String optionText;

    public ChoiceResponse(Choice choice) {
        this.id = choice.getId();
        this.optionText = choice.getOptionText();
    }
}
