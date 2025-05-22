package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.RoleUpdateRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.model.Course;
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

    @GetMapping("/{adminId}/users")
    public ResponseEntity<?> getUsers(@PathVariable String adminId){
        List<UserResponse> users=adminService.getAllUsersByInstitution(adminId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{adminId}/courses")
    public ResponseEntity<?> getCourses(@PathVariable String adminId){
        List<CourseResponse> courses= adminService.getAllCoursesByInstitution(adminId);
        return new ResponseEntity<>(courses,HttpStatus.OK);
    }

}
