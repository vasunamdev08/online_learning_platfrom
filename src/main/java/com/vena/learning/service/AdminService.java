package com.vena.learning.service;

import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void registerAdmin(RegisterRequest adminRequest);
    void saveAdmin(RegisterRequest adminRequest);
    boolean isExists(String email,String username);
    Admin getAdminByEmail(String email);
    Admin getAdminById(String id);
    Admin getAdminByUsername(String username);
}
