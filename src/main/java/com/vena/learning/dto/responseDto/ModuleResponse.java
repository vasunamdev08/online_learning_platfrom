package com.vena.learning.dto.responseDto;

import com.vena.learning.enums.Type;
import com.vena.learning.model.Module;
import lombok.Data;

@Data
public class ModuleResponse {
    private String id;
    private String title;
    private String content;
    private int sequence;
    private Type type;

    public ModuleResponse(Module module){
        this.id = module.getId();
        this.title = module.getTitle();
        this.content = module.getContent();
        this.sequence = module.getSequence();
        this.type = module.getType();
    }

}

