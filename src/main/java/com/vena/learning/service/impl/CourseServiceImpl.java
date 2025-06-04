package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.QuizResponse;
import com.vena.learning.dto.responseDto.QuizResponseWrapper;
import com.vena.learning.enums.Type;
import com.vena.learning.exception.customException.CourseExceptions.ApprovedCourseNotFoundException;
import com.vena.learning.exception.customException.CourseExceptions.CourseAlreadyExistsForInstructorException;
import com.vena.learning.exception.customException.CourseExceptions.CourseDescriptionEmptyException;
import com.vena.learning.exception.customException.CourseExceptions.CourseNotFoundByIdException;
import com.vena.learning.exception.customException.CourseExceptions.CourseTitleEmptyException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorIdMissingException;
import com.vena.learning.exception.customException.ModuleExceptions.EmptyModulesListException;
import com.vena.learning.exception.customException.ModuleExceptions.FirstModuleMustBeIntroductionException;
import com.vena.learning.exception.customException.ModuleExceptions.ModuleNotFound;
import com.vena.learning.exception.customException.ModuleExceptions.ModuleNotFoundByIdAndCourseIdException;
import com.vena.learning.exception.customException.ModuleExceptions.MultipleConclusionModulesException;
import com.vena.learning.exception.customException.ModuleExceptions.MultipleIntroductionModulesException;
import com.vena.learning.model.Course;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.Module;
import com.vena.learning.repository.CourseRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.InstructorService;
import com.vena.learning.service.QuizService;
import com.vena.learning.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    @Lazy
    private QuizService quizService;

    @Autowired
    @Lazy
    private ModuleService moduleService;

    @Override
    public Course addCourse(Course course) {
        if (course.getTitle() == null || course.getDescription() == null || course.getInstructor() == null) {
            throw new CourseDescriptionEmptyException("Course details are incomplete");
        }
        return courseRepository.save(course);
    }

    @Override
    public List<CourseResponse> getApprovedCourses() {
        List<Course> courses = courseRepository.getAllApprovedCourses().orElseThrow(
                () -> new ApprovedCourseNotFoundException("No approved courses found")
        );
        return courses.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse getCourseResponseByCourseId(String id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new CourseNotFoundByIdException("Course not found with id: " + id)
        );
        return new CourseResponse(course);
    }

    @Override
    public Course getCourseById(String id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new CourseNotFoundByIdException("Course not found with id: " + id)
        );
    }

    @Override
    public List<CourseResponse> getCoursesByInstructorId(String instructorId) {
        List<Course> courses = courseRepository.getCoursesByInstructorId(instructorId).orElseThrow(
                () -> new CourseNotFoundByIdException("No courses found for instructor with id: " + instructorId)
        );
        if(courses.isEmpty()) {
            throw new CourseNotFoundByIdException("No courses found for instructor with id: " + instructorId);
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
            throw new CourseTitleEmptyException("Course title cannot be empty.");
        }
        if (course.getDescription() == null || course.getDescription().trim().isEmpty()) {
            throw new CourseDescriptionEmptyException("Course description cannot be empty.");
        }
        if (course.getInstructorId() == null || course.getInstructorId().trim().isEmpty()) {
            throw new InstructorIdMissingException("Instructor ID cannot be empty.");
        }

        // 2. Check for duplicate course title for the instructor
        boolean courseExists = courseRepository.existsByTitleAndInstructor(
                course.getTitle(),
                instructorService.getInstructorById(course.getInstructorId())
        );
        if (courseExists) {
            throw new CourseAlreadyExistsForInstructorException("Course already exists with this title for the instructor.");
        }

        // 3. Convert incoming ModuleRequests to Module entities
        List<Module> incomingModules = course.getModules().stream().map(req -> {
            Module m = new Module();
            m.setId(req.getId()); // for update deduplication
            m.setTitle(req.getTitle());
            m.setContent(req.getContent());
            m.setType(req.getType());
            m.setSequence(req.getSequence());
            return m;
        }).toList();

        // 4. Fetch existing modules if updating
        List<Module> existingModules = new ArrayList<>();
        if (course.getCourseId() != null && !course.getCourseId().isBlank()) {
            existingModules = moduleService.getModulesByCourseId(course.getCourseId());
        }

        // 5. Merge incoming and existing modules (excluding overwritten ones)
        Set<String> incomingIds = incomingModules.stream()
                .map(Module::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Module> mergedModules = Stream.concat(
                existingModules.stream()
                        .filter(m -> m.getId() != null && !incomingIds.contains(m.getId())),
                incomingModules.stream()
        ).toList();

        // 6. Run merged validation
        validateModuleSequenceAndTypeConstraints(mergedModules);
    }

    @Override
    public void validateModuleSequenceAndTypeConstraints(List<Module> modules) {
        if (modules == null || modules.isEmpty()) {
            throw new EmptyModulesListException("Modules list cannot be empty.");
        }

        Map<Type, Long> typeCounts = modules.stream()
                .collect(Collectors.groupingBy(Module::getType, Collectors.counting()));

        // 1. Must have exactly one INTRODUCTION and one CONCLUSION
        if (typeCounts.getOrDefault(Type.Introduction, 0L) != 1) {
            throw new MultipleIntroductionModulesException("There must be exactly one INTRODUCTION module.");
        }
        if (typeCounts.getOrDefault(Type.Conclusion, 0L) != 1) {
            throw new MultipleConclusionModulesException("There must be exactly one CONCLUSION module.");
        }

        // 2. First sequence module should be INTRODUCTION
        Module first = modules.stream()
                .min(Comparator.comparingInt(Module::getSequence))
                .orElseThrow(() -> new ModuleNotFound("No modules found."));

        if (first.getType() != Type.Introduction) {
            throw new FirstModuleMustBeIntroductionException("The first module (lowest sequence) must be of type INTRODUCTION.");
        }

        // 3. Last sequence module should be CONCLUSION
        Module last = modules.stream()
                .max(Comparator.comparingInt(Module::getSequence))
                .orElseThrow(() -> new ModuleNotFound("No modules found."));

        if (last.getType() != Type.Conclusion) {
            throw new MultipleConclusionModulesException("The last module (highest sequence) must be of type CONCLUSION.");
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

    @Override
    public QuizResponseWrapper getAllQuizzesForCourse(String courseId) {
        //validate course existence
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundByIdException("Course not found with ID: " + courseId);
        }
        List<QuizResponse> quizResponsesList = quizService.getQuizzesByCourseId(courseId);
        return new QuizResponseWrapper(quizResponsesList);
    }
}
