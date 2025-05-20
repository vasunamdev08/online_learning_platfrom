package com.vena.learning.model;

import com.vena.learning.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private String name;
    @Column(unique = true)
    private String email;
    private String institution;

    @Enumerated(EnumType.STRING)
    private Role role; // STUDENT, INSTRUCTOR, ADMIN
}

