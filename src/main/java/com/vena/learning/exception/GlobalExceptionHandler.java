package com.vena.learning.exception;

import com.vena.learning.exception.customException.AdminException.AdminNotFoundByEmailException;
import com.vena.learning.exception.customException.AdminException.AdminNotFoundByIdException;
import com.vena.learning.exception.customException.CourseExceptions.CourseDescriptionEmptyException;
import com.vena.learning.exception.customException.CourseExceptions.CourseNotFoundByIdException;
import com.vena.learning.exception.customException.CourseExceptions.CourseQuizAccessDeniedDueToDeletionException;
import com.vena.learning.exception.customException.CourseExceptions.CourseQuizAccessDeniedDueToUnapprovedStatusException;
import com.vena.learning.exception.customException.CourseExceptions.CourseTitleEmptyException;
import com.vena.learning.exception.customException.CourseExceptions.CourseViewNotAuthorizedException;
import com.vena.learning.exception.customException.InstitutionExceptions.InstitutionDetailsMissingException;
import com.vena.learning.exception.customException.InstitutionExceptions.NoCoursesFoundForInstitutionException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorIdMissingException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundForCourseException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorViewNotAuthorizedException;
import com.vena.learning.exception.customException.ModuleExceptions.EmptyModulesListException;
import com.vena.learning.exception.customException.ModuleExceptions.FirstModuleMustBeIntroductionException;
import com.vena.learning.exception.customException.ModuleExceptions.LastModuleMustBeConclusionException;
import com.vena.learning.exception.customException.ModuleExceptions.ModuleNotFoundByIdAndCourseIdException;
import com.vena.learning.exception.customException.ModuleExceptions.MultipleConclusionModulesException;
import com.vena.learning.exception.customException.ModuleExceptions.MultipleIntroductionModulesException;
import com.vena.learning.exception.customException.QuizExceptions.QuizNotFoundException;
import com.vena.learning.exception.customException.StudentExceptions.EnrollmentDoesNotExistException;
import com.vena.learning.exception.customException.StudentExceptions.EnrollmentNotFoundException;
import com.vena.learning.exception.customException.StudentExceptions.NoCoursesFoundForStudentException;
import com.vena.learning.exception.customException.StudentExceptions.StudentAlreadyEnrolledException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotEnrolledInCourseException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotFoundByIdException;
import com.vena.learning.exception.customException.StudentExceptions.StudentViewNotAuthorizedException;
import com.vena.learning.exception.customException.UserExceptions.UserDeletionNotAuthorizedException;
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

    @ExceptionHandler(CourseQuizAccessDeniedDueToUnapprovedStatusException.class)
    public ResponseEntity<ProblemDetail> handleCourseQuizAccessDeniedDueToUnapprovedStatusException(CourseQuizAccessDeniedDueToUnapprovedStatusException e,
                                                                                                       HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("Quiz Access Denied");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "QUIZ_ACCESS_DENIED_UNAPPROVED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseTitleEmptyException.class)
    public ResponseEntity<ProblemDetail> handleCourseTitleEmptyException(CourseTitleEmptyException e,
                                                                          HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Course Title Missing");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_TITLE_EMPTY");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseViewNotAuthorizedException.class)
    public ResponseEntity<ProblemDetail> handleCourseViewNotAuthorizedException(CourseViewNotAuthorizedException e,
                                                                                 HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("Unauthorized Course View");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_VIEW_UNAUTHORIZED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(problemDetail);
    }

    @ExceptionHandler(InstitutionDetailsMissingException.class)
    public ResponseEntity<ProblemDetail> handleInstitutionDetailsMissingException(InstitutionDetailsMissingException e,
                                                                                   HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Institution Details Missing");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTITUTION_DETAILS_MISSING");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(NoCoursesFoundForInstitutionException.class)
    public ResponseEntity<ProblemDetail> handleNoCoursesFoundForInstitutionException(NoCoursesFoundForInstitutionException e,
                                                                                       HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("No Courses Found for Institution");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "NO_COURSES_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(InstructorNotFoundForCourseException.class)
    public ResponseEntity<ProblemDetail> handleInstructorNotFoundForCourseException(InstructorNotFoundForCourseException e,
                                                                                     HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Instructor Not Found for Course");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTRUCTOR_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(InstructorIdMissingException.class)
    public ResponseEntity<ProblemDetail> handleInstructorIdMissingException(InstructorIdMissingException e,
                                                                             HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Instructor ID Missing");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTRUCTOR_ID_MISSING");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(InstructorViewNotAuthorizedException.class)
    public ResponseEntity<ProblemDetail> handleInstructorViewNotAuthorizedException(InstructorViewNotAuthorizedException e,
                                                                                     HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("Unauthorized Instructor View");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTRUCTOR_VIEW_UNAUTHORIZED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(problemDetail);
    }

    @ExceptionHandler(EmptyModulesListException.class)
    public ResponseEntity<ProblemDetail> handleEmptyModulesListException(EmptyModulesListException e,
                                                                           HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Empty Modules List");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "EMPTY_MODULES_LIST");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(FirstModuleMustBeIntroductionException.class)
    public ResponseEntity<ProblemDetail> handleFirstModuleMustBeIntroductionException(FirstModuleMustBeIntroductionException e,
                                                                                       HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("First Module Must Be Introduction");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "FIRST_MODULE_INTRO_REQUIRED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(LastModuleMustBeConclusionException.class)
    public ResponseEntity<ProblemDetail> handleLastModuleMustBeConclusionException(LastModuleMustBeConclusionException e,
                                                                                   HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Last Module Must Be Conclusion");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "LAST_MODULE_CONCLUSION_REQUIRED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(ModuleNotFoundByIdAndCourseIdException.class)
    public ResponseEntity<ProblemDetail> handleModuleNotFoundByIdAndCourseIdException(ModuleNotFoundByIdAndCourseIdException e,
                                                                                         HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Module Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "MODULE_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(MultipleConclusionModulesException.class)
    public ResponseEntity<ProblemDetail> handleMultipleConclusionModulesException(MultipleConclusionModulesException e,
                                                                                   HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Multiple Conclusion Modules Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "MULTIPLE_CONCLUSION_MODULES");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(MultipleIntroductionModulesException.class)
    public ResponseEntity<ProblemDetail> handleMultipleIntroductionModulesException(MultipleIntroductionModulesException e,
                                                                                     HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Multiple Introduction Modules Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "MULTIPLE_INTRODUCTION_MODULES");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleQuizNotFoundException(QuizNotFoundException e,
                                                                      HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Quiz Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "QUIZ_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(EnrollmentDoesNotExistException.class)
    public ResponseEntity<ProblemDetail> handleEnrollmentDoesNotExistException(EnrollmentDoesNotExistException e,
                                                                                HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Enrollment Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "ENROLLMENT_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(EnrollmentNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEnrollmentNotFoundException(EnrollmentNotFoundException e,
                                                                            HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Enrollment Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "ENROLLMENT_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(NoCoursesFoundForStudentException.class)
    public ResponseEntity<ProblemDetail> handleNoCoursesFoundForStudentException(NoCoursesFoundForStudentException e,
                                                                                  HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("No Courses Found for Student");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "NO_COURSES_FOUND_FOR_STUDENT");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    public ResponseEntity<ProblemDetail> handleStudentAlreadyEnrolledException(StudentAlreadyEnrolledException e,
                                                                                HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Student Already Enrolled");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "STUDENT_ALREADY_ENROLLED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(problemDetail);
    }

    @ExceptionHandler(StudentNotEnrolledInCourseException.class)
    public ResponseEntity<ProblemDetail> handleStudentNotEnrolledInCourseException(StudentNotEnrolledInCourseException e,
                                                                                     HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Student Not Enrolled in Course");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "STUDENT_NOT_ENROLLED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(StudentNotFoundByIdException.class)
    public ResponseEntity<ProblemDetail> handleStudentNotFoundByIdException(StudentNotFoundByIdException e,
                                                                             HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Student Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "STUDENT_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(StudentViewNotAuthorizedException.class)
    public ResponseEntity<ProblemDetail> handleStudentViewNotAuthorizedException(StudentViewNotAuthorizedException e,
                                                                                 HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("Unauthorized Student View");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "STUDENT_VIEW_UNAUTHORIZED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(problemDetail);
    }

    @ExceptionHandler(UserDeletionNotAuthorizedException.class)
    public ResponseEntity<ProblemDetail> handleUserDeletionNotAuthorizedException(UserDeletionNotAuthorizedException e,
                                                                                   HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("User Deletion Not Authorized");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "USER_DELETION_NOT_AUTHORIZED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(problemDetail);
    }
}
