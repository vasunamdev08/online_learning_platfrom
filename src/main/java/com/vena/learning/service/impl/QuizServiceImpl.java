package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.CreateQuizRequest;
import com.vena.learning.dto.responseDto.QuestionResponse;
import com.vena.learning.dto.responseDto.QuizResponse;
import com.vena.learning.model.Course;
import com.vena.learning.model.Quiz;
import com.vena.learning.repository.ChoiceRepository;
import com.vena.learning.repository.QuestionRepository;
import com.vena.learning.repository.QuizRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.QuizService;
import com.vena.learning.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepo;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    @Lazy
    private CourseService courseService;
    @Autowired
    @Lazy
    private QuestionRepository questionRepository;
    @Autowired
    @Lazy
    private ChoiceRepository choiceRepository;

    @Override
    public List<QuizResponse> getQuizzesByCourseId(String courseId) {
       List<Quiz> quizzes = quizRepo.findByCourseId(courseId);
        return quizzes.stream().map(quiz -> new QuizResponse(quiz.getId(), quiz.getTitle())).collect(Collectors.toList());
    }
    @Override
    public Quiz getQuizById(String quizId) {
        return quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
    }
    @Override
    public List<QuestionResponse> getQuizQuestions(String studentId, String courseId, String quizId) {

        //apply check if the student exists and is enrolled in the course.
        studentService.getStudentById(studentId);
        if (!enrollmentService.isEnrolled(studentId, courseId)) {
            throw new RuntimeException("Student with id " + studentId + " is not enrolled in the course.");
        }

        //applying check that the course is not deleted and isApproved.
        if (!courseService.getCourseById(courseId).isApproved()) {
            throw new RuntimeException("Cannot access the course quiz as it course is not approved.");
        }
        if (courseService.getCourseById(courseId).isDeleted()){
            throw new RuntimeException("Cannot access the course quiz as it course is deleted.");
        }

        //apply check for the course module completion.

        Quiz quiz = quizRepo.findByIdAndCourseId(quizId, courseId).orElseThrow(() -> new RuntimeException("Quiz not found."));
        return quiz.getQuestions().stream().map(QuestionResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createQuiz(Course course, CreateQuizRequest request) {

    }
}
