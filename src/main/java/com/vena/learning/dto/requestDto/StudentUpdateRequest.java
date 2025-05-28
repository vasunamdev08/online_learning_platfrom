package com.vena.learning.dto.requestDto;

import lombok.Data;

@Data
public class StudentUpdateRequest {
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String institution;
}
