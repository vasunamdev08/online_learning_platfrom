package com.vena.learning.dto.responseDto;

import com.vena.learning.model.User;
import lombok.Data;

@Data
public class UserResponse {
    String id;
    String name;
    String email;
    String username;
    String institution;
    String role;

    public UserResponse(User user){
        this.id= user.getId();
        this.name= user.getName();
        this.email= user.getEmail();
        this.username= user.getUsername();
        this.institution= user.getInstitution();
        this.role= user.getRole().toString();
    }
}
