package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.RegisterResponse;
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
        RegisterResponse registerResponse = studentService.registerStudent(studentRequest);
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest adminRequest) {
        RegisterResponse registerResponse = adminService.registerAdmin(adminRequest);
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/instructor")
    public ResponseEntity<?> registerInstructor(@RequestBody RegisterRequest instructorRequest) {
        RegisterResponse registerResponse = instructorService.registerInstructor(instructorRequest);
        return ResponseEntity.ok(registerResponse);
    }
}
