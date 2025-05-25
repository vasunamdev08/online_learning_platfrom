package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.QuizSubmissionRequest;
import com.vena.learning.model.Choice;
import com.vena.learning.model.Quiz;
import com.vena.learning.model.QuizAttempt;
import com.vena.learning.model.Student;
import com.vena.learning.repository.ChoiceRepository;
import com.vena.learning.repository.QuizAttemptRepository;
import com.vena.learning.repository.QuizRepository;
import com.vena.learning.repository.StudentRepository;
import com.vena.learning.service.QuizAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class QuizAttemptImpl implements QuizAttemptService {
    @Autowired
    private QuizAttemptRepository quizAttemptRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private ChoiceRepository choiceRepo;

    @Override
    public void submitQuiz(String studentId, String courseId, String quizId, QuizSubmissionRequest request) {
        //so here we need to attemptNumber++ and set the score if(attemptNumber == 1)
        //also set the attemptDate

        Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        Integer attempts = quizAttemptRepo.findMaxAttemptNumberByStudentIdAndQuizId(studentId, quizId);
        int newAttemptNumber = (attempts == null) ? 1 : attempts + 1;

        QuizAttempt attempt = new QuizAttempt();
        attempt.setStudent(student);
        attempt.setQuiz(quiz);
        attempt.setAttemptDate(LocalDateTime.now());
        attempt.setAttemptNumber(newAttemptNumber);

        if (newAttemptNumber == 1) {
            int score = 0;
            for (QuizSubmissionRequest.AnswerSubmission answer : request.getAnswers()) {
                Choice choice = choiceRepo.findById(answer.getSelectedChoiceId())
                        .orElseThrow(() -> new RuntimeException("Choice not found"));
                if (choice.isCorrect()) {
                    score++;
                }
            }
            attempt.setScore(score);
        }
        quizAttemptRepo.save(attempt);
    }
}
