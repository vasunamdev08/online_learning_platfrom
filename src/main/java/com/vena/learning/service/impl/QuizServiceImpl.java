package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.ChoiceRequest;
import com.vena.learning.dto.requestDto.QuestionRequest;
import com.vena.learning.model.Course;
import com.vena.learning.model.Quiz;
import com.vena.learning.repository.QuizRepository;
import com.vena.learning.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepo;

    @Override
    public List<QuestionRequest> getQuizQuestions(String courseId, String quizId) {
        Quiz quiz = quizRepo.findByIdAndCourseId(quizId, courseId).orElseThrow(() -> new RuntimeException("Quiz not found."));
        Course course = quiz.getCourse();

        if (!course.isApproved() || course.isDeleted()) {
            throw new RuntimeException("Cannot access questions: Course is either not approved ordeleted.");
        }

        return quiz.getQuestions().stream().map(question -> {
            QuestionRequest response = new QuestionRequest();
            response.setId(question.getId());
            response.setQuestionText(question.getQuestion());

            response.setChoices(question.getChoices().stream().map(
            choice -> {
                ChoiceRequest resOptions = new ChoiceRequest();
                resOptions.setId(choice.getId());
                resOptions.setOptionText(choice.getOptionText());
                resOptions.setCorrect(choice.isCorrect());
                return resOptions;
            }).collect(Collectors.toList()));
            return response;
        }).collect(Collectors.toList());
    }
}
