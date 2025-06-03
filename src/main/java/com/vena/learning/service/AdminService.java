package com.vena.learning.service;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.CourseStats;
import com.vena.learning.dto.responseDto.CourseStatusResponse;
import com.vena.learning.dto.responseDto.InstructorStatResponse;
import com.vena.learning.dto.responseDto.StatisticsResponse;
import com.vena.learning.dto.responseDto.StudentStatResponse;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.model.Admin;
import com.vena.learning.model.Course;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.Student;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface AdminService {
    void registerAdmin(RegisterRequest adminRequest);
    void saveAdmin(RegisterRequest adminRequest);

    boolean isExists(String email,String username);
    boolean isExistsByEmail(String email);
    boolean isExistsByUsername(String username);

    Admin getAdminByEmail(String email);
    Admin getAdminById(String id);
    Admin getAdminByUsername(String username);

    List<UserResponse> getAllUsersByInstitution(String adminId);
    List<CourseResponse> getAllCoursesByInstitution(String adminId);

    String getInstitutionByAdminId(String adminId);

    void deleteCourse(String courseId, String adminId);
    void approveCourse(String courseId, String adminId);
    void deleteUser(String adminId, String userId);

    StatisticsResponse getStatistics(String adminId);

    CourseStatusResponse getCoursesByApprovalStatus(String adminId);

    List<UserResponse> getAllStudentsByInstitution(String adminId);

    List<UserResponse> getAllInstructorsByInstitution(String adminId);

    StudentStatResponse getStudentById(String adminId, String studentId);

    InstructorStatResponse getInstructorById(String adminId, String instructorId);

    CourseStats getCourseById(String adminId, String courseId);
}
