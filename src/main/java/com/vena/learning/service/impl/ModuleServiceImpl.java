package com.vena.learning.service.impl;

import com.vena.learning.repository.ModuleRepository;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {
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
}
