package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.CreateChoiceRequest;
import com.vena.learning.dto.requestDto.CreateQuestionRequest;
import com.vena.learning.model.Choice;
import com.vena.learning.model.Question;
import com.vena.learning.model.Quiz;
import com.vena.learning.repository.ChoiceRepository;
import com.vena.learning.repository.QuestionRepository;
import com.vena.learning.repository.QuizRepository;
import com.vena.learning.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void addQuestionToQuiz(String quizId, CreateQuestionRequest questionRequest) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with ID: " + quizId));

        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setQuestion(questionRequest.getQuestion());
        question.setQuiz(quiz);

        questionRepository.save(question);
    }

    @Override
    public void addChoiceToQuestion(String questionId, CreateChoiceRequest choiceRequest) {
        Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        Choice choice = new Choice();
        choice.setOptionText(choiceRequest.getOptionText());
        choice.setCorrect(choiceRequest.isCorrect());
        choice.setQuestion(question);

        choiceRepository.save(choice);
    }

}
