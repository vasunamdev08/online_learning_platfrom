package com.vena.learning.service;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.model.Module;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModuleService {
    Module getModuleById(String studentId, String courseId, String moduleId);
    CourseResponse addModuleToCourse(CourseRequest courseRequest);
    List<Integer> getSequencesByCourseId(String courseId);
    List<Module> getModulesByCourseId(String courseId);
    Module updateModule(Module existingModule, ModuleRequest request);
    Module fetchModuleByIdOrThrow(String moduleId);
    void validateModuleTypeBySequence(ModuleRequest request, List<Module> existingModules);

}
