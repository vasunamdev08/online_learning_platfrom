package com.vena.learning.service;

import com.vena.learning.dto.responseDto.QuizResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface QuizService {
    List<QuizResponse> getQuizzesByCourseId(String courseId);
}
