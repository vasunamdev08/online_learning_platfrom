package com.vena.learning.service;

import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Admin;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AdminService {
    void registerAdmin(RegisterRequest adminRequest);
    void saveAdmin(RegisterRequest adminRequest);
    boolean isExists(String email,String username);
    Optional<Admin> getAdminByEmail(String email);
    Optional<Admin> getAdminById(String id);
    Optional<Admin> getAdminByUsername(String username);
}
