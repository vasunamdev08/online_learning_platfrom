package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.AnswerSubmissionRequest;
import com.vena.learning.dto.requestDto.QuizSubmissionRequest;
import com.vena.learning.dto.responseDto.QuizAttemptResponse;
import com.vena.learning.enums.Grade;
import com.vena.learning.model.Choice;
import com.vena.learning.model.Quiz;
import com.vena.learning.model.QuizAttempt;
import com.vena.learning.model.Student;
import com.vena.learning.repository.ChoiceRepository;
import com.vena.learning.repository.QuizAttemptRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.QuizAttemptService;
import com.vena.learning.service.QuizService;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class QuizAttemptImpl implements QuizAttemptService {
    @Autowired
    private QuizAttemptRepository quizAttemptRepo;

    @Autowired
    private StudentService studentService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private ChoiceRepository choiceRepo;

    @Autowired
    private EnrollmentService enrollmentService;

    @Override
    public QuizAttemptResponse submitQuiz(QuizSubmissionRequest request) {
        //so here we need to attemptNumber++ and set the score if(attemptNumber == 1)
        //also set the attemptDate
        Student student = studentService.getStudentById(request.getStudentId());
        Quiz quiz = quizService.getQuizById(request.getQuizId());
        //applying check that the student is enrolled in the course.
        if (!enrollmentService.isEnrolled(request.getStudentId(), request.getCourseId())) {
            throw new RuntimeException("Student is not enrolled in the course. Cannot attempt the quiz.");
        }
        Integer attemptNumber = calculateAttemptNumber(request.getStudentId(), request.getQuizId());
        int newAttemptNumber = (attemptNumber == null) ? 1 : attemptNumber + 1;
        //allowing user to take max 2 attempts.
        if (newAttemptNumber > 2) {
            throw new RuntimeException("Maximum of 2 attempts reached. You cannot take the quiz again.");
        }
        int score = calculateScore(request);
        QuizAttempt attempt = createQuizAttempt(student, quiz, newAttemptNumber, score);
        quizAttemptRepo.save(attempt);
        enrollmentService.setGradeBasedOnBestAttempt(request.getStudentId(), request.getCourseId(), request.getQuizId(), newAttemptNumber);
        return new QuizAttemptResponse(attempt);
    }

    public Integer calculateAttemptNumber(String studentId, String quizId) {
        return quizAttemptRepo.findMaxAttemptNumberByStudentIdAndQuizId(studentId, quizId);
    }

    public QuizAttempt createQuizAttempt(Student student, Quiz quiz, int attemptNumber, int score) {
        QuizAttempt attempt = new QuizAttempt();
        attempt.setStudent(student);
        attempt.setQuiz(quiz);
        attempt.setAttemptNumber(attemptNumber);
        attempt.setAttemptDate(LocalDateTime.now());
        attempt.setScore(score);
        return attempt;
    }

    public int calculateScore(QuizSubmissionRequest request) {
        //here we are setting the score for each attempt, and we can retrieve the value
        //when required to evaluate grade then fetch attempt number one.
        int score = 0;
        for (AnswerSubmissionRequest answer : request.getAnswers()) {
            Choice choice = choiceRepo.findById(answer.getSelectedChoiceId())
                    .orElseThrow(() -> new RuntimeException("Choice not found"));
            if (choice.isCorrect()) {
                score++;
            }
        }
        return score;
    }

    public List<QuizAttempt> findByStudentIdAndQuizId(String studentId, String quizId) {
        return quizAttemptRepo.findByStudentIdAndQuizId(studentId, quizId);
    }
}
