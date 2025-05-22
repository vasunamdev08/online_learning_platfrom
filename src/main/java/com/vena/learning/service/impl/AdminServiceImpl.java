package com.vena.learning.service.impl;

import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.model.Course;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.Student;
import com.vena.learning.model.User;
import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.model.Admin;
import com.vena.learning.enums.Role;
import com.vena.learning.repository.AdminRepository;
import com.vena.learning.service.AdminService;
import com.vena.learning.service.InstructorService;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;


    private List<UserResponse> mapToUserResponse(List<User> users) {
        return users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public void registerAdmin(RegisterRequest adminRequest) {
        if(adminRequest.getName() == null || adminRequest.getEmail() == null || adminRequest.getUsername() == null || adminRequest.getPassword() == null) {
            throw new RuntimeException("Admin details are incomplete");
        }
        if(isExists(adminRequest.getEmail(), adminRequest.getUsername())) {
            throw new RuntimeException("Admin already exists with email: " + adminRequest.getEmail() + " or username: " + adminRequest.getUsername());
        }
        saveAdmin(adminRequest);
    }

    @Override
    public void saveAdmin(RegisterRequest adminRequest) {
        Admin admin = new Admin();
        admin.setName(adminRequest.getName());
        admin.setEmail(adminRequest.getEmail());
        admin.setUsername(adminRequest.getUsername());
        admin.setPassword(adminRequest.getPassword());
        admin.setInstitution(adminRequest.getInstitution());
        admin.setRole(Role.ADMIN);
        adminRepository.save(admin);
    }

    @Override
    public boolean isExists(String email, String username) {
        return adminRepository.existsByEmail(email) || adminRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Admin not found with email: " + email)
        );
    }

    @Override
    public Admin getAdminById(String id) {
        return adminRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Admin not found with id: " + id)
        );
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Admin not found with username: " + username)
        );
    }

    @Override
    public List<CourseResponse> getAllCoursesByInstitution(String adminId){
        String institution = getInstitutionByAdminId(adminId);
        if(institution==null || institution.trim().isEmpty()){
            throw new IllegalArgumentException("Institution cannot be null or empty");
        }

        List<Instructor> instructors= instructorService.getAllInstructorByInstitute(institution);
        List<Course> collect = instructors.stream()
                .flatMap(instructor -> instructor.getCourses().stream())
                .collect(Collectors.toList());
        return collect.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllUsersByInstitution(String adminId) {
        String institution = getInstitutionByAdminId(adminId);
        if(institution==null || institution.trim().isEmpty()){
            throw new IllegalArgumentException("Institution cannot be null or empty");
        }
        List<User> allUsers=new ArrayList<>();
        allUsers.addAll(studentService.getAllStudentByInstitute(institution));
        allUsers.addAll(instructorService.getAllInstructorByInstitute(institution));
        return mapToUserResponse(allUsers);
    }

    @Override
    public String getInstitutionByAdminId(String adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new RuntimeException("Admin not found with id: " + adminId)
        );
        return admin.getInstitution();
    }

    @Override
    public void deleteUser(String userId) {
        Optional<Student> studentOpt = studentService.findById(userId);
        if (studentOpt.isPresent()) {
            studentService.deleteStudent(userId);
            return;
        }

        Optional<Instructor> instructorOpt = instructorService.findById(userId);
        if (instructorOpt.isPresent()) {
            instructorService.deleteInstructor(userId);
            return;
        }

        throw new RuntimeException("User not found with id: " + userId);
    }


}
