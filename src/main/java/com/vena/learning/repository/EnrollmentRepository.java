package com.vena.learning.repository;

import com.vena.learning.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    Optional<Enrollment> findByStudentIdAndCourseId(String studentId, String courseId);

    @Query(nativeQuery = true, value = "SELECT is_enrolled FROM enrollment WHERE student_id = ?1 AND course_id = ?2")
    boolean isEnrolledByStudentIdAndCourseId(String studentId, String courseId);
}
