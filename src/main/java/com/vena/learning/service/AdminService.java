package com.vena.learning.service;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.model.Course;
import com.vena.learning.model.User;
import com.vena.learning.model.Admin;
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
}
