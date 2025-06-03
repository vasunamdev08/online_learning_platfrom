package com.vena.learning.model;

import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.enums.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int sequence;
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Module() {
    }

    public Module(ModuleRequest moduleRequest, Course course) {
        this.title = moduleRequest.getTitle();
        this.content = moduleRequest.getContent();
        this.type = moduleRequest.getType();
        this.sequence = moduleRequest.getSequence();
        this.course = course;
    }
}