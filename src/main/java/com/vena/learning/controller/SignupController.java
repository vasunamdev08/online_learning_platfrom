package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.service.AdminService;
import com.vena.learning.service.InstructorService;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class SignupController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/student")
    public ResponseEntity<?> registerStudent(@RequestBody RegisterRequest studentRequest) {
        System.out.println("Registering student: " + studentRequest);
        studentService.registerStudent(studentRequest);
        return ResponseEntity.ok("Student registered successfully");
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest adminRequest) {
        adminService.registerAdmin(adminRequest);
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/instructor")
    public ResponseEntity<?> registerInstructor(@RequestBody RegisterRequest instructorRequest) {
        instructorService.registerInstructor(instructorRequest);
        return ResponseEntity.ok("Instructor registered successfully");
    }
}
