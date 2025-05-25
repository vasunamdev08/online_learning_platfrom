package com.vena.learning.controller;

import com.vena.learning.dto.requestDto.CreateChoiceRequest;
import com.vena.learning.dto.requestDto.CreateCourseDTO;
import com.vena.learning.dto.requestDto.CreateQuestionRequest;
import com.vena.learning.dto.requestDto.CreateQuizRequest;
import com.vena.learning.dto.requestDto.GradeUpdateRequest;
import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.requestDto.ReorderModulesRequest;
import com.vena.learning.dto.requestDto.UpdateCourseDTO;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.service.InstructorService;
import com.vena.learning.service.ModuleService;
import com.vena.learning.service.QuestionService;
import com.vena.learning.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor/courses")
@RequiredArgsConstructor
@Validated
public class InstructorController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    private final InstructorService instructorService;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getInstructorCourses(@RequestParam String instructorId) {
        List<CourseResponse> courses = instructorService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CreateCourseDTO courseDTO,
                                                  @RequestParam String instructorId) {
        CourseResponse createdCourse = instructorService.createCourse(courseDTO, instructorId);
        return ResponseEntity.ok(createdCourse);
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable String courseId,
                                               @RequestBody UpdateCourseDTO updateDTO) {
        instructorService.updateCourse(courseId, updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Course updated successfully");
    }


    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        instructorService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully");
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<String> addModuleToCourse(
            @PathVariable String courseId,
            @RequestBody ModuleRequest moduleRequest) {

        moduleService.addModuleToCourse(courseId, moduleRequest);
        return ResponseEntity.ok("Module added successfully");
    }

    @PutMapping("/modules/{moduleId}")
    public ResponseEntity<String> updateModule(
            @PathVariable String moduleId,
            @RequestBody ModuleRequest moduleRequest) {

        moduleService.updateModule(moduleId, moduleRequest);
        return ResponseEntity.ok("Module updated successfully");
    }

    @DeleteMapping("/modules/{moduleId}")
    public ResponseEntity<String> deleteModule(@PathVariable String moduleId) {
        moduleService.deleteModule(moduleId);
        return ResponseEntity.ok("Module deleted successfully");
    }

    @PutMapping("/courses/{courseId}/modules/reorder")
    public ResponseEntity<String> reorderModules(
            @PathVariable String courseId,
            @RequestBody ReorderModulesRequest reorderRequest) {

        moduleService.reorderModules(courseId, reorderRequest);
        return ResponseEntity.ok("Modules reordered successfully");
    }

    @PostMapping("/courses/{courseId}/quizzes")
    public ResponseEntity<String> createQuiz(
            @PathVariable String courseId,
            @RequestBody CreateQuizRequest quizRequest) {

        quizService.createQuiz(courseId, quizRequest);
        return ResponseEntity.ok("Quiz created successfully");
    }

    @PostMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<String> addQuestionToQuiz(
            @PathVariable String quizId,
            @RequestBody CreateQuestionRequest questionRequest) {

        questionService.addQuestionToQuiz(quizId, questionRequest);
        return ResponseEntity.ok("Question added successfully");
    }

    @PostMapping("/questions/{questionId}/options")
    public ResponseEntity<String> addOptionToQuestion(
            @PathVariable String questionId,
            @RequestBody CreateChoiceRequest choiceRequest) {

        questionService.addChoiceToQuestion(questionId, choiceRequest);
        return ResponseEntity.ok("Option added successfully");
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<UserResponse>> getStudentsByCourse(@PathVariable String courseId) {
        List<UserResponse> students = instructorService.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/courses/{courseId}/grades")
    public ResponseEntity<String> updateStudentGrade(
            @PathVariable String courseId,
            @RequestBody GradeUpdateRequest gradeRequest) {

        instructorService.updateStudentGrade(courseId, gradeRequest);
        return ResponseEntity.ok("Grade updated successfully");
    }
}
