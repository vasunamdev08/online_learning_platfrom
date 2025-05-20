package com.vena.learning.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String institution;
}
