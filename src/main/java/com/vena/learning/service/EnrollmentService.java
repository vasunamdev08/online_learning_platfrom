package com.vena.learning.service;

import com.vena.learning.dto.EnrollmentRequestDTO;

public interface EnrollmentService {
    public String enrollStudent(String courseId, EnrollmentRequestDTO enrollmentRequestDTO);
}
