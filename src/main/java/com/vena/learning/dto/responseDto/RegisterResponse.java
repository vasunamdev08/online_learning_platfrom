package com.vena.learning.dto.responseDto;

import com.vena.learning.model.User;
import lombok.Data;

@Data
public class RegisterResponse {
    private String username;
    private String password;
    private String name;
    private String email;
    private String institution;

    public RegisterResponse(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
        this.institution = user.getInstitution();
    }

}
