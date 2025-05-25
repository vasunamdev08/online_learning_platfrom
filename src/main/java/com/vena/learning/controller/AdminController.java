package com.vena.learning.controller;

import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.StatisticsResponse;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.service.AdminService;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/{adminId}/users")
    public ResponseEntity<?> getUsers(@PathVariable String adminId) {
        List<UserResponse> users = adminService.getAllUsersByInstitution(adminId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{adminId}/courses")
    public ResponseEntity<?> getCourses(@PathVariable String adminId) {
        List<CourseResponse> courses = adminService.getAllCoursesByInstitution(adminId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @DeleteMapping("/{adminId}/delete/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String adminId, @PathVariable String userId) {
        adminService.deleteUser(adminId, userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{adminId}/delete/course/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String adminId, @PathVariable String courseId) {
        adminService.deleteCourse(adminId,courseId);
        return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{adminId}/approve/course/{courseId}")
    public ResponseEntity<?> approveCourse(@PathVariable String adminId, @PathVariable String courseId) {
        adminService.approveCourse(courseId, adminId);
        return new ResponseEntity<>("Course approved successfully", HttpStatus.OK);
    }

    @GetMapping("/{adminId}/statistics")
    public ResponseEntity<?> getStatistics(@PathVariable String adminId) {
        StatisticsResponse statistics = adminService.getStatistics(adminId);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
