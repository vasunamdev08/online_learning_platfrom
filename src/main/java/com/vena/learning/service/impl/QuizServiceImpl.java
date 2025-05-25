package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.CreateQuizRequest;
import com.vena.learning.model.Course;
import com.vena.learning.model.Quiz;
import com.vena.learning.repository.CourseRepository;
import com.vena.learning.repository.QuizRepository;
import com.vena.learning.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public void createQuiz(String courseId, CreateQuizRequest quizRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Quiz quiz = new Quiz();
        quiz.setId(UUID.randomUUID().toString());
        quiz.setTitle(quizRequest.getTitle());
        quiz.setCourse(course);

        quizRepository.save(quiz);
    }
}

