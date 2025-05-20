package com.vena.learning.service;

import com.vena.learning.dto.AdminInstitution;
import com.vena.learning.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsersByInstitution(AdminInstitution adminInstitution);
}
