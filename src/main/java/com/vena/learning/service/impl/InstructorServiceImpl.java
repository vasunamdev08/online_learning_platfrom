package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.ModuleResponse;
import com.vena.learning.model.Course;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.Module;
import com.vena.learning.enums.Role;
import com.vena.learning.repository.InstructorRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.InstructorService;
import com.vena.learning.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    @Lazy
    private CourseService courseService;

    @Autowired
    @Lazy
    private ModuleService moduleService;

    @Override
    public Instructor getInstructorById(String id) {
        return instructorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Instructor not found with id: " + id)
        );
    }

    @Override
    public Instructor getInstructorByUsername(String username) {
        return instructorRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Instructor not found with username: " + username)
        );
    }

    @Override
    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.getInstructorByEmail(email).orElseThrow(
                () -> new RuntimeException("Instructor not found with email: " + email)
        );
    }

    @Override
    public boolean isExist(String email, String username) {
        return instructorRepository.getInstructorByEmail(email).isPresent() || instructorRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean isExistsByUsername(String username) {
        return instructorRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return instructorRepository.existsByEmail(email);
    }

    @Override
    public void saveInstructor(RegisterRequest instructorRequest) {
        Instructor instructor = new Instructor();
        instructor.setName(instructorRequest.getName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setUsername(instructorRequest.getUsername());
        instructor.setPassword(instructorRequest.getPassword());
        instructor.setInstitution(instructorRequest.getInstitution());
        instructor.setRole(Role.INSTRUCTOR);
        instructorRepository.save(instructor);
    }

    @Override
    public void registerInstructor(RegisterRequest instructorRequest) {
        if(isExist(instructorRequest.getEmail(), instructorRequest.getUsername())) {
            throw new RuntimeException("Instructor already exists with email or username");
        }
        if(instructorRequest.getName() == null || instructorRequest.getEmail() == null || instructorRequest.getUsername() == null || instructorRequest.getPassword() == null) {
            throw new RuntimeException("Instructor details are incomplete");
        }
        saveInstructor(instructorRequest);
    }

    @Override
    public List<Instructor> getAllInstructorByInstitute(String institution){
        return instructorRepository.findByInstitution(institution).orElseThrow(()-> new RuntimeException("Instructor not found"));
    }
    @Override
    public void deleteInstructor(String userId) {
        Instructor instructor = getInstructorById(userId);
        instructorRepository.delete(instructor);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
     public  List<CourseResponse> getCoursesByInstructor(String instructorId) {
        if (!instructorRepository.existsById(instructorId)) {
            throw new RuntimeException("Instructor with ID " + instructorId + " does not exist");
        }
        return courseService.getCoursesByInstructorId(instructorId);
    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        if (!instructorRepository.existsById(request.getInstructorId())) {
            throw new RuntimeException("Instructor with ID " + request.getInstructorId() + " is not present");
        }
        return courseService.addCourseWithModules(request);
    }

    @Override
    public CourseResponse updateInstructorCourse(CourseRequest request) {
        return courseService.updateCourse(request);
    }

    @Override
    public void deleteInstructorCourse(String courseId) {
        courseService.deleteCourse(courseId);
    }

    @Override
    public CourseResponse addModuleToCourse(CourseRequest courseRequest) {
        return moduleService.addModuleToCourse(courseRequest);
    }

    @Override
    public boolean isInstructorExist(String userId) {
        return instructorRepository.existsById(userId);
    }

    @Override
    public ModuleResponse updateModule(ModuleRequest moduleRequest) {
        if (moduleRequest.getId() == null || moduleRequest.getCourseId() == null) {
            throw new IllegalArgumentException("Module ID and Course ID must not be null.");
        }

        // Validate course exists
        Course course = courseService.getCourseById(moduleRequest.getCourseId());

        // Validate module exists
        Module existingModule = moduleService.fetchModuleByIdOrThrow(moduleRequest.getId());

        // Ensure module belongs to the course
        if (!existingModule.getCourse().getId().equals(moduleRequest.getCourseId())) {
            throw new IllegalArgumentException("Module does not belong to the specified course.");
        }

        // Fetch all modules for the course
        List<Module> allModules = moduleService.getModulesByCourseId(moduleRequest.getCourseId());

        // Validate based on sequence position
        moduleService.validateModuleTypeBySequence(moduleRequest, allModules);

        // Persist changes
        Module updated = moduleService.updateModule(existingModule, moduleRequest);
        return new ModuleResponse(updated);
    }

}
