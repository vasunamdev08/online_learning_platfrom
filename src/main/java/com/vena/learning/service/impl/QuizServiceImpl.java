package com.vena.learning.service.impl;

import com.vena.learning.dto.responseDto.QuestionResponse;
import com.vena.learning.dto.responseDto.QuizResponse;
import com.vena.learning.exception.customException.CourseExceptions.CourseApprovalNotAuthorizedException;
import com.vena.learning.exception.customException.CourseExceptions.CourseQuizAccessDeniedDueToDeletionException;
import com.vena.learning.exception.customException.CourseExceptions.CourseQuizAccessDeniedDueToUnapprovedStatusException;
import com.vena.learning.exception.customException.QuizExceptions.QuizNotFoundException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotEnrolledInCourseException;
import com.vena.learning.model.Quiz;
import com.vena.learning.repository.QuizRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.QuizService;
import com.vena.learning.service.StudentService;
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

    @Override
    public List<QuizResponse> getQuizzesByCourseId(String courseId) {
       List<Quiz> quizzes = quizRepo.findByCourseId(courseId);
        return quizzes.stream().map(quiz -> new QuizResponse(quiz.getId(), quiz.getTitle())).collect(Collectors.toList());
    }
    @Override
    public Quiz getQuizById(String quizId) {
        return quizRepo.findById(quizId).orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));
    }
    @Override
    public List<QuestionResponse> getQuizQuestions(String studentId, String courseId, String quizId) {

        //apply check if the student exists and is enrolled in the course.
        studentService.getStudentById(studentId);
        if (!enrollmentService.isEnrolled(studentId, courseId)) {
            throw new StudentNotEnrolledInCourseException("Student with id " + studentId + " is not enrolled in the course.");
        }

        //applying check that the course is not deleted and isApproved.
        if (!courseService.getCourseById(courseId).isApproved()) {
            throw new CourseQuizAccessDeniedDueToUnapprovedStatusException("Cannot access the course quiz as it course is not approved.");
        }
        if (courseService.getCourseById(courseId).isDeleted()){
            throw new CourseQuizAccessDeniedDueToDeletionException("Cannot access the course quiz as it course is deleted.");
        }

        //apply check for the course module completion.

        Quiz quiz = quizRepo.findByIdAndCourseId(quizId, courseId).orElseThrow(() -> new RuntimeException("Quiz not found."));
        return quiz.getQuestions().stream().map(QuestionResponse::new).collect(Collectors.toList());
    }
}
