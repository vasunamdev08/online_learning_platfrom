package com.vena.learning.repository;


import com.vena.learning.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, String> {
    @Query("SELECT MAX(q.attemptNumber) FROM QuizAttempt q WHERE q.student.id = :studentId AND q.quiz.id = :quizId")
    Integer findMaxAttemptNumberByStudentIdAndQuizId(@Param("studentId") String studentId, @Param("quizId") String quizId);
}
