package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.model.Admin;
import com.vena.learning.enums.Role;
import com.vena.learning.repository.AdminRepository;
import com.vena.learning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
