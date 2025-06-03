package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.AdminApproveCourse;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.CourseStats;
import com.vena.learning.dto.responseDto.CourseStatusResponse;
import com.vena.learning.dto.responseDto.InstructorStatResponse;
import com.vena.learning.dto.responseDto.StatisticsResponse;
import com.vena.learning.dto.responseDto.StudentStatResponse;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.model.Course;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.Student;
import com.vena.learning.service.AdminService;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        CourseStatusResponse response = adminService.getCoursesByApprovalStatus(adminId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{adminId}/students")
    public ResponseEntity<?> getStudents(@PathVariable String adminId) {
        List<UserResponse> students = adminService.getAllStudentsByInstitution(adminId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{adminId}/instructors")
    public ResponseEntity<?> getInstructors(@PathVariable String adminId) {
        List<UserResponse> instructors = adminService.getAllInstructorsByInstitution(adminId);
        return new ResponseEntity<>(instructors, HttpStatus.OK);
    }

    @GetMapping("/{adminId}/statistics")
    public ResponseEntity<?> getStatistics(@PathVariable String adminId) {
        StatisticsResponse statistics = adminService.getStatistics(adminId);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @PutMapping("/approve/course")
    public ResponseEntity<?> approveCourse(@RequestBody AdminApproveCourse adminApproveCourse) {
        adminService.approveCourse(adminApproveCourse);
        return new ResponseEntity<>("Course approved successfully", HttpStatus.OK);
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

    @GetMapping("/{adminId}/student/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable String adminId, @PathVariable String studentId) {
        StudentStatResponse student = adminService.getStudentById(adminId,studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/{adminId}/instructor/{instructorId}")
    public ResponseEntity<?> getInstructorById(@PathVariable String adminId, @PathVariable String instructorId) {
        InstructorStatResponse instructor = adminService.getInstructorById(adminId,instructorId);
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }

    @GetMapping("/{adminId}/course/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable String adminId, @PathVariable String courseId){
        CourseStats course=adminService.getCourseById(adminId,courseId);
        return new ResponseEntity<>(course,HttpStatus.OK);
    }
}
