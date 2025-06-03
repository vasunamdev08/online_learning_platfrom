package com.vena.learning.service;

import com.vena.learning.model.Module;
import org.springframework.stereotype.Service;

@Service
public interface ModuleService {
    Module getModuleById(String studentId, String courseId, String moduleId);
    Module saveModule(Module module);
}
