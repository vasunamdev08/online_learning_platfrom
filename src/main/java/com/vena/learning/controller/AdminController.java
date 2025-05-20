package com.vena.learning.controller;

import com.vena.learning.dto.AdminInstitution;
import com.vena.learning.model.User;
import com.vena.learning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users/{adminInstitution}")
    public ResponseEntity<?> getUsers(@PathVariable AdminInstitution adminInstitution){
        List<User> users=adminService.getAllUsersByInstitution(adminInstitution);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
