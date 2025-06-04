package com.vena.learning.exception;

import com.vena.learning.exception.customException.AdminException.AdminNotFoundByEmailException;
import com.vena.learning.exception.customException.AdminException.AdminNotFoundByIdException;
import com.vena.learning.exception.customException.QuizException.*;
import com.vena.learning.exception.customException.StudentException.*;
import com.vena.learning.exception.customException.UserException.UserDeletionNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error");
        error.put("message", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Invalid argument: " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
    }


    @ExceptionHandler(AdminNotFoundByEmailException.class)
    public ResponseEntity<ProblemDetail> handleAdminNotFoundByEmailException(AdminNotFoundByEmailException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Admin Not Found");
        problemDetail.setProperty("email", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problemDetail);
    }

    @ExceptionHandler(AdminNotFoundByIdException.class)
    public ResponseEntity<ProblemDetail> handleAdminNotFoundByIdException(AdminNotFoundByIdException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Admin Not Found");
        problemDetail.setProperty("id", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problemDetail);
    }

    // ------------ Quiz Exceptions --------------

    @ExceptionHandler(ChoiceNotFoundException.class)
    public ResponseEntity<String> handleChoiceNotFound(ChoiceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MaxQuizAttemptsExceededException.class)
    public ResponseEntity<String> handleMaxQuizAttempts(MaxQuizAttemptsExceededException e) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(e.getMessage());
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<String> handleQuizNotFound(QuizNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // ------------ Student Exceptions -------------

    @ExceptionHandler(EnrollmentDoesNotExistException.class)
    public ResponseEntity<String> handleEnrollmentDoesNotExist(EnrollmentDoesNotExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(EnrollmentNotFoundException.class)
    public ResponseEntity<String> handleEnrollmentNotFound(EnrollmentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NoCoursesFoundForStudentException.class)
    public ResponseEntity<String> handleNoCoursesFound(NoCoursesFoundForStudentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    public ResponseEntity<String> handleStudentAlreadyEnrolled(StudentAlreadyEnrolledException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<String> handleStudentAlreadyExists(StudentAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(StudentDetailIncompleteException.class)
    public ResponseEntity<String> handleStudentDetailIncomplete(StudentDetailIncompleteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(StudentNotEnrolledInCourseException.class)
    public ResponseEntity<String> handleStudentNotEnrolled(StudentNotEnrolledInCourseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(StudentNotFoundByEmailException.class)
    public ResponseEntity<String> handleStudentNotFoundByEmail(StudentNotFoundByEmailException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(StudentNotFoundByIdException.class)
    public ResponseEntity<String> handleStudentNotFoundById(StudentNotFoundByIdException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(StudentNotFoundByUsername.class)
    public ResponseEntity<String> handleStudentNotFoundByUsername(StudentNotFoundByUsername e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFound(StudentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(StudentViewNotAuthorizedException.class)
    public ResponseEntity<String> handleStudentViewNotAuthorized(StudentViewNotAuthorizedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    // ------------- User Exceptions -----------------

    @ExceptionHandler(UserDeletionNotAuthorizedException.class)
    public ResponseEntity<String> handleUserDeletionNotAuthorized(UserDeletionNotAuthorizedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

}
