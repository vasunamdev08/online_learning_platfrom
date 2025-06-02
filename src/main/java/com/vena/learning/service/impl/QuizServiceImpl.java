package com.vena.learning.service.impl;

import com.vena.learning.dto.responseDto.QuizResponse;
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
    public List<QuizResponse> getQuizzesByCourseId(String courseId) {
       List<Quiz> quizzes = quizRepo.findByCourseId(courseId);
        return quizzes.stream().map(quiz -> new QuizResponse(quiz.getId(), quiz.getTitle())).collect(Collectors.toList());
    }

}
