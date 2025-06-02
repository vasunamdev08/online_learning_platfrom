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
import com.vena.learning.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    @Lazy
    private ModuleService moduleService;

    @Override
    public Course addCourse(Course course) {
        if (course.getTitle() == null || course.getDescription() == null || course.getInstructor() == null) {
            throw new RuntimeException("Course details are incomplete");
        }
        return courseRepository.save(course);
    }

    @Override
    public List<CourseResponse> getApprovedCourses() {
        List<Course> courses = courseRepository.getAllApprovedCourses().orElseThrow(
                () -> new RuntimeException("No approved courses found")
        );
        return courses.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse getCourseResponseByCourseId(String id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course not found with id: " + id)
        );
        return new CourseResponse(course);
    }

    @Override
    public Course getCourseById(String id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course not found with id: " + id)
        );
    }

    @Override
    public List<CourseResponse> getCoursesByInstructorId(String instructorId) {
        List<Course> courses = courseRepository.getCoursesByInstructorId(instructorId).orElseThrow(
                () -> new RuntimeException("No courses found for instructor with id: " + instructorId)
        );
        if(courses.isEmpty()) {
            throw new RuntimeException("No courses found for instructor with id: " + instructorId);
        }
        return courses.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public Course addCourse(CourseRequest course) {
        Course newCourse = new Course(course);
        Instructor instructor = instructorService.getInstructorById(course.getInstructorId());
        newCourse.setInstructor(instructor);
        return courseRepository.save(newCourse);
    }

    @Override
    public CourseResponse addCourseWithModules(CourseRequest course) {
        validateCourseRequest(course);
        return new CourseResponse(addCourse(course));
    }

    @Override
    public void validateCourseRequest(CourseRequest course) {
        // 1. Basic field validation
        if (course.getTitle() == null || course.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Course title cannot be empty.");
        }
        if (course.getDescription() == null || course.getDescription().trim().isEmpty()) {
            throw new RuntimeException("Course description cannot be empty.");
        }
        if (course.getInstructorId() == null || course.getInstructorId().trim().isEmpty()) {
            throw new RuntimeException("Instructor ID cannot be empty.");
        }

        // 2. Duplicate course title check for instructor
        boolean courseExists = courseRepository.existsByTitleAndInstructor(
                course.getTitle(),
                instructorService.getInstructorById(course.getInstructorId())
        );
        if (courseExists) {
            throw new RuntimeException("Course already exists with this title for the instructor.");
        }

        // 3. Module-level validation
        List<ModuleRequest> modules = course.getModules();
        if (modules == null || modules.size() < 3) {
            throw new RuntimeException("Course must have at least 3 modules.");
        }

        Set<Integer> seenSequences = new HashSet<>();
        Map<Type, Long> typeCounts = new EnumMap<>(Type.class);

        for (ModuleRequest module : modules) {
            // Check duplicate sequence in request
            if (!seenSequences.add(module.getSequence())) {
                throw new RuntimeException("Duplicate sequence number in request: " + module.getSequence());
            }

            // Count module types
            typeCounts.merge(module.getType(), 1L, Long::sum);
        }

        if (typeCounts.getOrDefault(Type.Introduction, 0L) != 1) {
            throw new RuntimeException("Course must have exactly one INTRODUCTION module.");
        }
        if (typeCounts.getOrDefault(Type.Lesson, 0L) < 1) {
            throw new RuntimeException("Course must have at least one LESSON module.");
        }
        if (typeCounts.getOrDefault(Type.Conclusion, 0L) != 1) {
            throw new RuntimeException("Course must have exactly one CONCLUSION module.");
        }

        // 4. Check for duplicate sequence numbers in DB
        if (course.getCourseId() != null && !course.getCourseId().isBlank()) {
            List<Integer> existingSequences = moduleService.getSequencesByCourseId(course.getCourseId());

            for (Integer sequence : seenSequences) {
                if (existingSequences.contains(sequence)) {
                    throw new RuntimeException("Module sequence " + sequence + " already exists in the course.");
                }
            }
        }
    }

    @Override
    public CourseResponse updateCourse(CourseRequest request) {
        Course course = getCourseById(request.getCourseId());
        Instructor instructor = instructorService.getInstructorById(request.getInstructorId());

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setInstructor(instructor);

        Course updatedCourse = courseRepository.save(course);
        return new CourseResponse(updatedCourse);
    }

    @Override
    public void deleteCourse(String courseId) {
        Course course = getCourseById(courseId);

        // Perform soft delete
        course.setDeleted(true);

        courseRepository.save(course);
    }

}
