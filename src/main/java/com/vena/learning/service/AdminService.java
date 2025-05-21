package com.vena.learning.service;

import com.vena.learning.dto.AdminInstitution;
import com.vena.learning.model.User;
import com.vena.learning.dto.RegisterRequest;
import com.vena.learning.model.Admin;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public interface AdminService {
    List<User> getAllUsersByInstitution(String institution);
    void registerAdmin(RegisterRequest adminRequest);
    void saveAdmin(RegisterRequest adminRequest);
    boolean isExists(String email,String username);
    Admin getAdminByEmail(String email);
    Admin getAdminById(String id);
    Admin getAdminByUsername(String username);
}
