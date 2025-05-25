package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.enums.Type;
import com.vena.learning.model.Course;
import com.vena.learning.model.Instructor;
import com.vena.learning.repository.CourseRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final InstructorService instructorService;

    @Override
    public Course addCourse(Course course) {
        if (course.getTitle() == null || course.getDescription() == null || course.getInstructor() == null) {
            throw new IllegalArgumentException("Course details are incomplete");
        }
        return courseRepository.save(course);
    }

    @Override
    public Course addCourse(CourseRequest courseRequest) {
        Instructor instructor = instructorService.getInstructorById(courseRequest.getInstructorId())
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found with ID: " + courseRequest.getInstructorId()));

        Course course = new Course(courseRequest);
        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    @Override
    public CourseResponse addCourseWithModules(CourseRequest courseRequest) {
        validateCourseRequest(courseRequest);
        Course savedCourse = addCourse(courseRequest);
        return new CourseResponse(savedCourse);
    }

    @Override
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found with ID: " + id));
    }

    @Override
    public CourseResponse getCourseResponseByCourseId(String id) {
        return new CourseResponse(getCourseById(id));
    }

    @Override
    public List<CourseResponse> getApprovedCourses() {
        List<Course> courses = courseRepository.getAllApprovedCourses()
                .orElseThrow(() -> new NoSuchElementException("No approved courses found"));
        return courses.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getCoursesByInstructorId(String instructorId) {
        List<Course> courses = courseRepository.getCoursesByInstructorId(instructorId)
                .orElseThrow(() -> new NoSuchElementException("No courses found for instructor with ID: " + instructorId));
        return courses.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    // --- Private Helpers ---

    private void validateCourseRequest(CourseRequest course) {
        if (course.getTitle() == null || course.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be empty");
        }
        if (course.getDescription() == null || course.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Course description cannot be empty");
        }
        if (course.getInstructorId() == null || course.getInstructorId().isEmpty()) {
            throw new IllegalArgumentException("Instructor ID cannot be empty");
        }
        if (course.getModules() == null || course.getModules().size() < 3) {
            throw new IllegalArgumentException("Course must have at least 3 modules");
        }

        Instructor instructor = instructorService.getInstructorById(course.getInstructorId())
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found with ID: " + course.getInstructorId()));

        boolean courseExists = courseRepository.existsByTitleAndInstructor(course.getTitle(), instructor);
        if (courseExists) {
            throw new IllegalArgumentException("Course already exists with this title for the instructor.");
        }

        validateModuleStructure(course.getModules());
    }

    private void validateModuleStructure(List<ModuleRequest> modules) {
        Set<Integer> sequences = new HashSet<>();
        Set<Type> types = modules.stream()
                .peek(module -> {
                    if (!sequences.add(module.getSequence())) {
                        throw new IllegalArgumentException("Duplicate module sequence number found: " + module.getSequence());
                    }
                })
                .map(ModuleRequest::getType)
                .collect(Collectors.toSet());

        if (!types.containsAll(Set.of(Type.Introduction, Type.Lesson, Type.Conclusion))) {
            throw new IllegalArgumentException("Modules must include INTRODUCTION, at least one LESSON, and CONCLUSION.");
        }
    }
}
