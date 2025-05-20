package com.vena.learning.service.serviceImpl;

import com.vena.learning.dto.CourseDTO;
import com.vena.learning.dto.CreateCourseDTO;
import com.vena.learning.model.Course;
import com.vena.learning.model.User;
import com.vena.learning.repository.CourseRepository;
import com.vena.learning.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseDTO> getCoursesByInstructor(String instructorId) {
        List<Course> courses = courseRepository.findByInstructorId(instructorId);
        return courses.stream()
                .map(course -> new CourseDTO(course))
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO createCourse(CreateCourseDTO courseDTO, String instructorId) {
        Course course = new Course();
        course.setId(UUID.randomUUID().toString());
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setCategory(courseDTO.getCategory());

        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        course.getInstructors().add(instructor); // assuming Many-to-Many
        courseRepository.save(course);

        return new CourseDTO(course);
    }

}

