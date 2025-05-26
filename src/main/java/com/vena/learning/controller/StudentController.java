package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.EnrollmentRequest;
import com.vena.learning.enums.Grade;
import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
import com.vena.learning.model.Module;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.ModuleService;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private ModuleService moduleService;

    /*
    we can use this endpoint to get all courses that a student is enrolled in after logging in.
    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses(Principal principal){
        String email = principal.getName();
        Student student = studentService.getStudentByEmail(email).orElseThrow(
                () -> new RuntimeException("Student not found with email: " + email)
        );
        List<Course> courses = student.getEnrollments().stream().map(Enrollment::getCourse).collect(Collectors.toList());
        return ResponseEntity.ok(courses);
    }
    */
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<?> getAllCourses(@PathVariable String studentId) {
        List<Course> courses = studentService.getStudentCources(studentId);
        return ResponseEntity.ok(courses);
    }
    @PostMapping("/student/enroll")
    public ResponseEntity<?> enrollInCourse(@RequestBody EnrollmentRequest enrollmentRequest) {
        enrollmentService.enrollStudent(enrollmentRequest);
        return ResponseEntity.ok("Student enrolled in course successfully");
    }
    @PostMapping("/student/unenroll")
    public ResponseEntity<?> unenrollInCourse(@RequestBody EnrollmentRequest enrollmentRequest) {
        enrollmentService.unenrollStudent(enrollmentRequest);
        return ResponseEntity.ok("Student unenrolled from course successfully");
    }
    @GetMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable String studentId, @PathVariable String courseId) {
        Enrollment courseDetails = enrollmentService.getCourseDetailsByIds(studentId, courseId);
        return ResponseEntity.ok(courseDetails);
    }
    @GetMapping("/{studentId}/courses/{courseId}/module/{moduleId}")
    public ResponseEntity<?> getModuleById(@PathVariable String studentId, @PathVariable String courseId, @PathVariable String moduleId) {
        Module module = moduleService.getModuleById(studentId, courseId, moduleId);
        return ResponseEntity.ok(module);
    }

    @GetMapping("/{studentId}/courses/{courseId}/grade")
    public ResponseEntity<?> getGrades(@PathVariable String studentId, @PathVariable String courseId) {
        Grade grade = enrollmentService.getGradeByCourse(studentId, courseId);
        return ResponseEntity.ok(grade);
    }
}
