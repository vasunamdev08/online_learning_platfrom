package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.model.Course;
import com.vena.learning.model.Module;
import com.vena.learning.repository.ModuleRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private CourseService courseService;

    @Override
    public Module getModuleById(String studentId, String courseId, String moduleId) {
        if(!enrollmentService.isEnrolled(studentId, courseId)) {
            throw new RuntimeException("Student is not enrolled in the course");
        }

        return moduleRepository.findByIdAndCourseId(moduleId, courseId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId + " and Course ID: " + courseId));
    }

    @Override
    public CourseResponse addModuleToCourse(CourseRequest courseRequest) {
        courseService.validateCourseRequest(courseRequest);

        Course course = courseService.getCourseById(courseRequest.getCourseId());

        // Convert and save modules
        List<Module> newModules = courseRequest.getModules().stream()
                .map(moduleRequest -> new Module(moduleRequest, course))
                .collect(Collectors.toList());

        moduleRepository.saveAll(newModules);

        // Add to existing course's module list
        course.getModules().addAll(newModules);

        return new CourseResponse(course);
    }

    @Override
    public List<Integer> getSequencesByCourseId(String courseId) {
        return moduleRepository.findByCourse_Id(courseId)
                .stream()
                .map(Module::getSequence)
                .toList();
    }

}
