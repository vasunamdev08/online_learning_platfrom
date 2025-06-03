package com.vena.learning.service.impl;

import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.enums.Type;
import com.vena.learning.model.Course;
import com.vena.learning.model.Module;
import com.vena.learning.repository.ModuleRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    @Lazy
    private ModuleRepository moduleRepository;

    @Autowired
    @Lazy
    private EnrollmentService enrollmentService;

    @Autowired
    @Lazy
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

    @Override
    public Module fetchModuleByIdOrThrow(String moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() -> new NoSuchElementException("Module not found with ID: " + moduleId));
    }

    @Override
    public Module updateModule(Module existingModule, ModuleRequest request) {
        existingModule.setTitle(request.getTitle());
        existingModule.setContent(request.getContent());
        existingModule.setSequence(request.getSequence());
        existingModule.setType(request.getType());
        return moduleRepository.save(existingModule);
    }

    @Override
    public void validateModuleTypeBySequence(ModuleRequest request, List<Module> modules) {
        List<Module> updatedList = new ArrayList<>();

        for (Module m : modules) {
            if (m.getId().equals(request.getId())) {
                Module updated = new Module();
                updated.setId(m.getId());
                updated.setTitle(request.getTitle());
                updated.setContent(request.getContent());
                updated.setSequence(request.getSequence());
                updated.setType(request.getType());
                updated.setCourse(m.getCourse());
                updatedList.add(updated);
            } else {
                updatedList.add(m);
            }
        }

        // Sort modules by sequence (actual order matters, not sequence value)
        updatedList.sort(Comparator.comparingInt(Module::getSequence));

        for (int i = 0; i < updatedList.size(); i++) {
            Module m = updatedList.get(i);
            Type expectedType;

            if (i == 0) {
                expectedType = Type.Introduction;
            } else if (i == updatedList.size() - 1) {
                expectedType = Type.Conclusion;
            } else {
                expectedType = Type.Lesson;
            }

            // Only validate the updated module
            if (m.getId().equals(request.getId()) && m.getType() != expectedType) {
                throw new IllegalArgumentException(
                        "Invalid module type. After update, the module at position " + (i + 1) +
                                " (sequence: " + m.getSequence() + ") must be of type " + expectedType +
                                ", but was " + m.getType());
            }
        }
    }

}
