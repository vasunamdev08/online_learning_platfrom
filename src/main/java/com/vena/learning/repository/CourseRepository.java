package com.vena.learning.repository;

import com.vena.learning.model.Course;
import com.vena.learning.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,String> {
    @Query(nativeQuery = true, value = "SELECT * FROM course WHERE is_approved = true")
    Optional<List<Course>> getAllApprovedCourses();
    Optional<List<Course>> getCoursesByInstructorId(String instructorId);

    boolean existsByTitleAndInstructor(String title, Instructor instructor);
}
