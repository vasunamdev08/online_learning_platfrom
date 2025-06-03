package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.EnrollmentRequest;
import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
import com.vena.learning.model.Quiz;
import com.vena.learning.model.QuizAttempt;
import com.vena.learning.model.Student;
import com.vena.learning.enums.Grade;
import com.vena.learning.repository.EnrollmentRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.QuizAttemptService;
import com.vena.learning.service.QuizService;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    @Lazy
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    @Lazy
    private QuizAttemptService quizAttemptService;
    @Autowired
    @Lazy
    private QuizService quizService;

    @Override
    public void enrollStudent(EnrollmentRequest enrollmentRequest) {
        String studentId = enrollmentRequest.getStudentId();
        String courseId = enrollmentRequest.getCourseId();
        if(isEnrolled(studentId, courseId)) {
            throw new RuntimeException("Student is already enrolled in the course");
        }
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);
        addEnrollment(student, course);
    }

    @Override
    public void addEnrollment(Student student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setIsEnrolled(true);
        enrollment.setIsCompleted(false);
        enrollment.setEnrollmentDate(new java.util.Date());
        enrollment.setAttempts(0);
        enrollment.setGrade(Grade.Pending);
        enrollment.setCompletionDate(null);
        enrollment.setProgress(0);
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void unenrollStudent(EnrollmentRequest enrollmentRequest) {
        String studentId = enrollmentRequest.getStudentId();
        String courseId = enrollmentRequest.getCourseId();
        if(!isEnrolled(studentId, courseId)) {
            throw new RuntimeException("Student is not enrolled in the course");
        }
        if(!isExists(studentId, courseId)) {
            throw new RuntimeException("Enrollment does not exist");
        }
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).get();
        enrollment.setIsEnrolled(false);
        enrollmentRepository.save(enrollment);
    }

    @Override
    public boolean isEnrolled(String studentId, String courseId) {
        return enrollmentRepository.existsByStudentIdAndCourseIdAndIsEnrolledTrue(studentId, courseId);
    }

    @Override
    public boolean isExists(String studentId, String courseId) {
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public Enrollment getCourseDetailsByIds(String studentId, String courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(
                () -> new RuntimeException("Enrollment not found with studentId: " + studentId + " and courseId: " + courseId)
        );
    }

    @Override
    public Grade getGradeByCourse(String studentId, String courseId) {
        Enrollment enrollment = getCourseDetailsByIds(studentId, courseId);
        return enrollment.getGrade();
    }


    @Override
    public void setGradeBasedOnBestAttempt(String studentId, String courseId, String quizId, int attemptNumber) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(() -> new RuntimeException("Enrollment not found"));
        List<QuizAttempt> attempts = quizAttemptService.findByStudentIdAndQuizId(studentId, quizId);
        if (attempts == null || attempts.isEmpty()) {
            throw new RuntimeException("No attempts found for the quiz.");
        }

        int score = attempts.stream().mapToInt(QuizAttempt::getScore).max().orElse(0);
        Quiz quiz = quizService.getQuizById(quizId);
        int totalQuestions = quiz.getQuestions().size();
        int bestScore = (score / totalQuestions) * 100;

        Grade grade;
        if (bestScore >= 90) {
            grade = Grade.A;
        } else if (bestScore >= 80) {
            grade = Grade.B;
        } else if (bestScore >= 70) {
            grade = Grade.C;
        } else if (bestScore >= 60) {
            grade = Grade.D;
        } else {
            grade = Grade.F;
        }
        enrollment.setGrade(grade);
        enrollment.setAttempts(attemptNumber);
        enrollmentRepository.save(enrollment);
    }
}
