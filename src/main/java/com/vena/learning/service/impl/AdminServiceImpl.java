package com.vena.learning.service.impl;

import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Admin;
import com.vena.learning.model.enums.Role;
import com.vena.learning.repository.AdminRepository;
import com.vena.learning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

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
        return getAdminByEmail(email).isPresent()|| getAdminByUsername(username).isPresent();
    }

    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Optional<Admin> getAdminById(String id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
