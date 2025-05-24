package com.vena.learning.repository;

import com.vena.learning.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,String> {
    Optional<Enrollment> findByStudentIdAndCourseId(String studentId, String courseId);
    boolean existsByStudentIdAndCourseId(String studentId, String courseId);

//    boolean getIsEnrolledByStudentIdAndCourseId(String studentId, String courseId);

    boolean existsByStudentIdAndCourseIdAndIsEnrolledTrue(String studentId, String courseId);
}
