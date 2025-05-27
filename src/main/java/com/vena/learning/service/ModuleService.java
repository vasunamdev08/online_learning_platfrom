package com.vena.learning.service;

import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.requestDto.ReorderModulesRequest;
import com.vena.learning.model.Module;
import org.springframework.stereotype.Service;

@Service
public interface ModuleService {
    Module getModuleById(String studentId, String courseId, String moduleId);
    void addModuleToCourse(String courseId, ModuleRequest moduleRequest);
    void updateModule(String moduleId, ModuleRequest moduleRequest);
    void deleteModule(String moduleId);
    void reorderModules(String courseId, ReorderModulesRequest reorderRequest);

}
