package com.vena.learning.service.impl;

import com.vena.learning.dto.AdminInstitution;
import com.vena.learning.model.User;
import com.vena.learning.repository.AdminRepository;
import com.vena.learning.repository.InstructorRepository;
import com.vena.learning.repository.StudentRepository;
import com.vena.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public List<User> getAllUsersByInstitution(AdminInstitution adminInstitution) {

        String institution= adminInstitution.getInstitution();

        if(institution==null || institution.trim().isEmpty()){
            throw new IllegalArgumentException("Institution cannot be null or empty");
        }
        List<User> allUsers=new ArrayList<>();
        allUsers.addAll(studentRepository.findByInstitution(institution));
        allUsers.addAll(instructorRepository.findByInstitution(institution));
        return allUsers;
    }
}
