package com.vena.learning.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class StudentRequestsDTO {
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String institution;

}
