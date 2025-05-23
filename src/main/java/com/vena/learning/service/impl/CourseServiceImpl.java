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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorService instructorService;

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

    private void validateCourseRequest(CourseRequest course) {
        if (course.getModules() == null || course.getModules().isEmpty()) {
            throw new RuntimeException("Course must have at least 3 modules");
        }
        boolean courseExists = courseRepository.existsByTitleAndInstructor(course.getTitle(), instructorService.getInstructorById(course.getInstructorId()));
        if (courseExists) {
            throw new RuntimeException("Course already exists with this title for the instructor.");
        }
        if (course.getTitle() == null || course.getTitle().isEmpty()) {
            throw new RuntimeException("Course title cannot be empty");
        }
        if (course.getDescription() == null || course.getDescription().isEmpty()) {
            throw new RuntimeException("Course description cannot be empty");
        }
        if (course.getInstructorId() == null || course.getInstructorId().isEmpty()) {
            throw new RuntimeException("Instructor ID cannot be empty");
        }

        Set<Integer> sequences = new HashSet<>();

        Set<Type> types = course.getModules().stream()
                .peek(module -> {
                    if (!sequences.add(module.getSequence())) {
                        throw new RuntimeException("Duplicate module sequence number found: " + module.getSequence());
                    }
                })
                .map(ModuleRequest::getType)
                .collect(Collectors.toSet());

        if (!types.containsAll(Set.of(Type.Introduction, Type.Lesson, Type.Conclusion))) {
            throw new RuntimeException("Modules must include INTRODUCTION, at least one LESSON, and CONCLUSION.");
        }
    }

}
