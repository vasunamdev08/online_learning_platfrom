package com.vena.learning.service.impl;

import com.vena.learning.dto.responseDto.QuestionResponse;
import com.vena.learning.model.Course;
import com.vena.learning.model.Quiz;
import com.vena.learning.repository.EnrollmentRepository;
import com.vena.learning.repository.QuizRepository;
import com.vena.learning.repository.StudentRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepo;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private EnrollmentRepository enrollmentRepo;
    @Autowired
    private CourseService courseService;

    @Override
    public List<QuestionResponse> getQuizQuestions(String studentId, String courseId, String quizId) {

        //apply check if the student exists and is enrolled in the course.
        studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException ("Student with the id " +  studentId +" do not exsits."));
        enrollmentRepo.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(() -> new RuntimeException("Student with id " + studentId + " is not enrolled in the course."));

        //applying check that the course is not deleted and isApproved.
        if (!courseService.getCourseById(courseId).isApproved()) {
            throw new RuntimeException("Cannot access the course quiz as it course is not approved.");
        }
        if (courseService.getCourseById(courseId).isDeleted()){
            throw new RuntimeException("Cannot access the course quiz as it course is deleted.");
        }

        //apply check for the course module completion.

        Quiz quiz = quizRepo.findByIdAndCourseId(quizId, courseId).orElseThrow(() -> new RuntimeException("Quiz not found."));
        Course course = quiz.getCourse();

        if (!course.isApproved() || course.isDeleted()) {
            throw new RuntimeException("Cannot access questions: Course is either not approved or deleted.");
        }
        return quiz.getQuestions().stream().map(QuestionResponse::new).collect(Collectors.toList());
    }
}
