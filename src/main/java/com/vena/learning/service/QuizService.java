package com.vena.learning.service;

import com.vena.learning.dto.responseDto.QuizResponse;
import com.vena.learning.model.Quiz;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface QuizService {
    List<QuizResponse> getQuizzesByCourseId(String courseId);
}
