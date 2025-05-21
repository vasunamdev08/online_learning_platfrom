package com.vena.learning.service.impl;

import com.vena.learning.dto.AdminInstitution;
import com.vena.learning.model.Course;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.User;
import com.vena.learning.repository.InstructorRepository;
import com.vena.learning.repository.StudentRepository;
import com.vena.learning.dto.RegisterRequest;
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

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;

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
    public List<User> getAllUsersByInstitution(String institution) {

        if(institution==null || institution.trim().isEmpty()){
            throw new IllegalArgumentException("Institution cannot be null or empty");
        }
        List<User> allUsers=new ArrayList<>();
        allUsers.addAll(studentService.getAllStudentByInstitute(institution));
        allUsers.addAll(instructorService.getAllInstructorByInstitute(institution));
        return allUsers;
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
        return getAdminByEmail(email)!=null|| getAdminByUsername(username)!=null;
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
    public List<Course> getAllCoursesByInstitution(String institution){
        if(institution==null || institution.trim().isEmpty()){
            throw new IllegalArgumentException("Institution cannot be null or empty");
        }

        List<Instructor> instructors= instructorService.getAllInstructorByInstitute(institution);
        List<Course> allCourses = new ArrayList<>();
        for (Instructor instructor : instructors) {
            allCourses.addAll(instructor.getCourses());
        }
        return allCourses;
    }
}
