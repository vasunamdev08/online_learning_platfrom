package com.vena.learning.exception;

import com.vena.learning.exception.customException.AdminException.AdminNotFoundByEmailException;
import com.vena.learning.exception.customException.AdminException.AdminNotFoundByIdException;
import com.vena.learning.exception.customException.CourseExceptions.CourseDescriptionEmptyException;
import com.vena.learning.exception.customException.CourseExceptions.CourseNotFoundByIdException;
import com.vena.learning.exception.customException.CourseExceptions.CourseQuizAccessDeniedDueToDeletionException;
import jakarta.servlet.http.HttpServletRequest;
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

    @ExceptionHandler(CourseDescriptionEmptyException.class)
    public ResponseEntity<ProblemDetail> handleCourseDescriptionEmptyException(CourseDescriptionEmptyException e,
                                                                               HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Course Description Missing");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_DESC_EMPTY");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseNotFoundByIdException.class)
    public ResponseEntity<ProblemDetail> handleCourseNotFoundByIdException(CourseNotFoundByIdException e,
                                                                           HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Course Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseQuizAccessDeniedDueToDeletionException.class)
    public ResponseEntity<ProblemDetail> handleCourseQuizAccessDeniedDueToDeletionException(CourseQuizAccessDeniedDueToDeletionException e,
                                                                                             HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("Quiz Access Denied");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "QUIZ_ACCESS_DENIED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(problemDetail);
    }
}
