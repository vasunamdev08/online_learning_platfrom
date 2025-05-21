package com.vena.learning.service.impl;

import com.vena.learning.dto.ChoiceDTO;
import com.vena.learning.dto.QuestionDTO;
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
    public List<QuestionDTO> getQuizQuestions(String courseId, String quizId) {
        Quiz quiz = quizRepo.findByIdAndCourseId(courseId, quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found."));

        return quiz.getQuestions().stream().map(question -> {
            QuestionDTO dto = new QuestionDTO();
            dto.setId(question.getId());
            dto.setQuestionText(question.getQuestion());
            dto.setChoices(question.getChoices().stream().map(choice -> {
                ChoiceDTO choiceDTO = new ChoiceDTO();
                choiceDTO.setId(choice.getId());
                choiceDTO.setOptionText(choice.getOptionText());
                choiceDTO.setCorrect(choice.isCorrect());
                return choiceDTO;
            }).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }
}
