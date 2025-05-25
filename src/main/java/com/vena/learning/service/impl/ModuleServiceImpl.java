package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.requestDto.ReorderModulesRequest;
import com.vena.learning.model.Course;
import com.vena.learning.model.Module;
import com.vena.learning.repository.CourseRepository;
import com.vena.learning.repository.ModuleRepository;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private EnrollmentService enrollmentService;

    @Override
    public Module getModuleById(String studentId, String courseId, String moduleId) {
        if(!enrollmentService.isEnrolled(studentId, courseId)) {
            throw new RuntimeException("Student is not enrolled in the course");
        }
        return moduleRepository.findById(moduleId).orElseThrow(
                () -> new RuntimeException("Module not found with id: " + moduleId)
        );
    }

    @Override
    public void addModuleToCourse(String courseId, ModuleRequest moduleRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Module module = new Module(moduleRequest, course);
        moduleRepository.save(module);
    }

    @Override
    public void updateModule(String moduleId, ModuleRequest moduleRequest) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId));

        module.setTitle(moduleRequest.getTitle());
        module.setContent(moduleRequest.getContent());
        module.setSequence(moduleRequest.getSequence());
        module.setType(moduleRequest.getType());

        moduleRepository.save(module);
    }

    @Override
    public void deleteModule(String moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId));
        moduleRepository.delete(module);
    }

    @Override
    public void reorderModules(String courseId, ReorderModulesRequest reorderRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Map<String, Integer> moduleSequenceMap = reorderRequest.getModuleSequenceMap();

        for (Module module : course.getModules()) {
            if (moduleSequenceMap.containsKey(module.getId())) {
                module.setSequence(moduleSequenceMap.get(module.getId()));
            }
        }

        moduleRepository.saveAll(course.getModules());
    }

}
