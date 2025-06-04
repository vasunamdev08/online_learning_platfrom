package com.vena.learning.config;

import com.vena.learning.service.AdminService;
import com.vena.learning.service.InstructorService;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private StudentService studentService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(studentService.isExistsByEmail(username)) {
            System.out.println("Loading user by email: " + username);
            return new CustomUserImpl(studentService.getStudentByEmail(username));
        } else if (instructorService.isExistsByEmail(username)) {
            return new CustomUserImpl(instructorService.getInstructorByEmail(username));
        }else {
            return new CustomUserImpl(adminService.getAdminByEmail(username));
        }
    }
}
