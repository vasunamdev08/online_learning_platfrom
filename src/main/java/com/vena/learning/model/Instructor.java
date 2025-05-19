package com.vena.learning.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    @Column(unique = true)
    private String email;

    private String department;

//     Uncomment if Course entity exists and is mapped
//     @OneToMany(mappedBy = "instructor")
//     private List<Course> courses;


}

