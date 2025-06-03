package com.vena.learning.service.impl;

import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
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
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private CourseService courseService;

    private void completeModule(String studentId, String courseId, int moduleSequence) {
        Enrollment enrollment = enrollmentService.getCourseDetailsByIds(studentId, courseId);

        Course course = enrollment.getCourse();

        System.out.println("Completing module: " + moduleSequence + " for student: " + studentId);

        if (moduleSequence < 0 || moduleSequence >= course.getModules().size()) {
            throw new IllegalArgumentException("Invalid module sequence");
        }

        int mask = enrollment.getProgressMask();
        mask |= (1 << moduleSequence); // Set the bit at the sequence index

        enrollment.setProgressMask(mask);
        if (isCourseComplete(enrollment)) {
            enrollment.setIsCompleted(true);
            enrollment.setCompletionDate(new java.util.Date());
        }
        enrollmentService.saveEnrollment(enrollment);
    }
    @Override
    public Module getModuleById(String studentId, String courseId, String moduleId) {
        if(!enrollmentService.isEnrolled(studentId, courseId)) {
            throw new RuntimeException("Student is not enrolled in the course");
        }
        Module module = moduleRepository.findById(moduleId).orElseThrow(
                () -> new RuntimeException("Module not found with id: " + moduleId)
        );
        completeModule(studentId,courseId, module.getSequence());
        return module;
    }

    private boolean isCourseComplete(Enrollment enrollment) {
        System.out.println("Checking if course is complete for student: " + enrollment.getStudent().getId());
        System.out.println("Progress Mask: " + enrollment.getProgressMask());
        System.out.println("Course Completion Mask: " + enrollment.getCourse().getCompletionMask());
        return enrollment.getProgressMask() == enrollment.getCourse().getCompletionMask();
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
        return moduleRepository.findByCourseId(courseId)
                .stream()
                .map(Module::getSequence)
                .toList();
    }

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public List<Module> getModulesByCourseId(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new RuntimeException("Course ID cannot be null or empty.");
        }

        return moduleRepository.findByCourseId(courseId);
    }

}
