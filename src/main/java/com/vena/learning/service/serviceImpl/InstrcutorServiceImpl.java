package com.vena.learning.service.serviceImpl;

import com.vena.learning.dto.CourseDTO;
import com.vena.learning.dto.CreateCourseDTO;
import com.vena.learning.model.Course;
import com.vena.learning.model.User;
import com.vena.learning.repository.CourseRepository;
import com.vena.learning.repository.UserRepository;
import com.vena.learning.service.InstructorService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public List<CourseDTO> getCoursesByInstructor(String instructorId) {
        return courseRepository.findByInstructors_Id(instructorId)
                .stream()
                .map(course -> {
                    CourseDTO dto = new CourseDTO();
                    dto.setId(course.getId());
                    dto.setTitle(course.getTitle());
                    dto.setDescription(course.getDescription());
                    dto.setCategory(course.getCategory());
                    return dto;
                })
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
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        course.setInstructors(new HashSet<>());
        course.getInstructors().add(instructor);

        courseRepository.save(course);

        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setCategory(course.getCategory());

        return dto;
    }
}

