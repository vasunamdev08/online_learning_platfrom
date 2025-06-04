package com.vena.learning.exception;

import com.vena.learning.exception.customException.AdminException.AdminAlreadyExistsException;
import com.vena.learning.exception.customException.AdminException.AdminNotFoundByEmailException;
import com.vena.learning.exception.customException.AdminException.AdminNotFoundByIdException;
import com.vena.learning.exception.customException.AdminException.AdminNotFoundByUsernameException;
import com.vena.learning.exception.customException.AdminException.IncompleteAdminDetailsException;
import com.vena.learning.exception.customException.CourseExceptions.ApprovedCourseNotFoundException;
import com.vena.learning.exception.customException.CourseExceptions.CourseAlreadyApprovedException;
import com.vena.learning.exception.customException.CourseExceptions.CourseAlreadyDeletedException;
import com.vena.learning.exception.customException.CourseExceptions.CourseAlreadyExistsForInstructorException;
import com.vena.learning.exception.customException.CourseExceptions.CourseApprovalNotAuthorizedException;
import com.vena.learning.exception.customException.CourseExceptions.CourseDeletionNotAuthorizedException;
import com.vena.learning.exception.customException.CourseExceptions.CourseDescriptionEmptyException;
import com.vena.learning.exception.customException.CourseExceptions.CourseIdEmptyException;
import com.vena.learning.exception.customException.CourseExceptions.CourseNotFoundByIdException;
import com.vena.learning.exception.customException.CourseExceptions.CourseQuizAccessDeniedDueToDeletionException;
import com.vena.learning.exception.customException.CourseExceptions.CourseQuizAccessDeniedDueToUnapprovedStatusException;
import com.vena.learning.exception.customException.CourseExceptions.CourseTitleEmptyException;
import com.vena.learning.exception.customException.CourseExceptions.CourseViewNotAuthorizedException;
import com.vena.learning.exception.customException.InstitutionExceptions.InstitutionDetailsMissingException;
import com.vena.learning.exception.customException.InstitutionExceptions.NoCoursesFoundForInstitutionException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorAlreadyExistException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorDetailMissingException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorIdMissingException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundByEmailException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundByIdException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundByUsernameException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundForCourseException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorViewNotAuthorizedException;
import com.vena.learning.exception.customException.ModuleExceptions.EmptyModulesListException;
import com.vena.learning.exception.customException.ModuleExceptions.FirstModuleMustBeIntroductionException;
import com.vena.learning.exception.customException.ModuleExceptions.InvalidModuleSequenceException;
import com.vena.learning.exception.customException.ModuleExceptions.LastModuleMustBeConclusionException;
import com.vena.learning.exception.customException.ModuleExceptions.ModuleNotFound;
import com.vena.learning.exception.customException.ModuleExceptions.ModuleNotFoundById;
import com.vena.learning.exception.customException.ModuleExceptions.ModuleNotFoundByIdAndCourseIdException;
import com.vena.learning.exception.customException.ModuleExceptions.ModuleValidationException;
import com.vena.learning.exception.customException.ModuleExceptions.MultipleConclusionModulesException;
import com.vena.learning.exception.customException.ModuleExceptions.MultipleIntroductionModulesException;
import com.vena.learning.exception.customException.QuizExceptions.ChoiceNotFoundException;
import com.vena.learning.exception.customException.QuizExceptions.MaxQuizAttemptsExceededException;
import com.vena.learning.exception.customException.QuizExceptions.QuizNotFoundException;
import com.vena.learning.exception.customException.StudentExceptions.EnrollmentDoesNotExistException;
import com.vena.learning.exception.customException.StudentExceptions.EnrollmentNotFoundException;
import com.vena.learning.exception.customException.StudentExceptions.NoCoursesFoundForStudentException;
import com.vena.learning.exception.customException.StudentExceptions.StudentAlreadyEnrolledException;
import com.vena.learning.exception.customException.StudentExceptions.StudentAlreadyExistsException;
import com.vena.learning.exception.customException.StudentExceptions.StudentDetailIncompleteException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotEnrolledInCourseException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotFoundByEmailException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotFoundByIdException;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotFoundByUsername;
import com.vena.learning.exception.customException.StudentExceptions.StudentNotFoundException;
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

    @ExceptionHandler(AdminNotFoundByUsernameException.class)
    public ResponseEntity<ProblemDetail> handleAdminNotFoundByUsernameException(AdminNotFoundByUsernameException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Admin Not Found");
        problemDetail.setProperty("username", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problemDetail);
    }

    @ExceptionHandler(IncompleteAdminDetailsException.class)
    public ResponseEntity<ProblemDetail> handleIncompleteAdminDetailsException(IncompleteAdminDetailsException e,
                                                                               HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Incomplete Admin Details");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INCOMPLETE_ADMIN_DETAILS");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleAdminAlreadyExistsException(AdminAlreadyExistsException e,
                                                                           HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Admin Already Exists");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "ADMIN_ALREADY_EXISTS");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseAlreadyApprovedException.class)
    public ResponseEntity<ProblemDetail> handleCourseAlreadyApprovedException(CourseAlreadyApprovedException e,
                                                                               HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Course Already Approved");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_ALREADY_APPROVED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseAlreadyDeletedException.class)
    public ResponseEntity<ProblemDetail> handleCourseAlreadyDeletedException(CourseAlreadyDeletedException e,
                                                                              HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GONE);
        problemDetail.setTitle("Course Already Deleted");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_ALREADY_DELETED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.GONE)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseDeletionNotAuthorizedException.class)
    public ResponseEntity<ProblemDetail> handleCourseDeletionNotAuthorizedException(CourseDeletionNotAuthorizedException e,
                                                                                     HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("Course Deletion Not Authorized");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_DELETION_NOT_AUTHORIZED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseAlreadyExistsForInstructorException.class)
    public ResponseEntity<ProblemDetail> handleCourseAlreadyExistsForInstructorException(CourseAlreadyExistsForInstructorException e,
                                                                                          HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Course Already Exists for Instructor");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_ALREADY_EXISTS");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseApprovalNotAuthorizedException.class)
    public ResponseEntity<ProblemDetail> handleCourseApprovalNotAuthorizedException(CourseApprovalNotAuthorizedException e,
                                                                                     HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("Course Approval Not Authorized");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_APPROVAL_NOT_AUTHORIZED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(problemDetail);
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

    @ExceptionHandler(ApprovedCourseNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleApprovedCourseNotFoundException(ApprovedCourseNotFoundException e,
                                                                                HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Approved Course Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "APPROVED_COURSE_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(ModuleNotFound.class)
    public ResponseEntity<ProblemDetail> handleModuleNotFound(ModuleNotFound e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Module Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "MODULE_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(ChoiceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleChoiceNotFoundException(ChoiceNotFoundException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Choice Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "CHOICE_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(MaxQuizAttemptsExceededException.class)
    public ResponseEntity<ProblemDetail> handleMaxQuizAttemptsExceededException(MaxQuizAttemptsExceededException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("Max Quiz Attempts Exceeded");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "MAX_QUIZ_ATTEMPTS_EXCEEDED");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(problemDetail);
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleStudentAlreadyExistsException(StudentAlreadyExistsException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Student Already Exists");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "STUDENT_ALREADY_EXISTS");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(problemDetail);
    }

    @ExceptionHandler(StudentDetailIncompleteException.class)
    public ResponseEntity<ProblemDetail> handleStudentDetailIncompleteException(StudentDetailIncompleteException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Student Detail Incomplete");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "STUDENT_DETAIL_INCOMPLETE");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(StudentNotFoundByEmailException.class)
    public ResponseEntity<ProblemDetail> handleStudentNotFoundByEmailException(StudentNotFoundByEmailException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Student Not Found by Email");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "STUDENT_NOT_FOUND_BY_EMAIL");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(StudentNotFoundByUsername.class)
    public ResponseEntity<ProblemDetail> handleStudentNotFoundByUsername(StudentNotFoundByUsername e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Student Not Found by Username");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "STUDENT_NOT_FOUND_BY_USERNAME");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleStudentNotFoundException(StudentNotFoundException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Student Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "STUDENT_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(InvalidModuleSequenceException.class)
    public ResponseEntity<ProblemDetail> handleInvalidModuleSequenceException(InvalidModuleSequenceException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid Module Sequence");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INVALID_MODULE_SEQUENCE");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(CourseIdEmptyException.class)
    public ResponseEntity<ProblemDetail> handleCourseIdEmptyException(CourseIdEmptyException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Course ID Missing");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "COURSE_ID_EMPTY");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(ModuleNotFoundById.class)
    public ResponseEntity<ProblemDetail> handleModuleNotFoundById(ModuleNotFoundById e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Module Not Found by ID");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "MODULE_NOT_FOUND_BY_ID");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(InstructorNotFoundByIdException.class)
    public ResponseEntity<ProblemDetail> handleInstructorNotFoundByIdException(InstructorNotFoundByIdException e,
                                                                                 HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Instructor Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTRUCTOR_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(InstructorNotFoundByUsernameException.class)
    public ResponseEntity<ProblemDetail> handleInstructorNotFoundByUsernameException(InstructorNotFoundByUsernameException e,
                                                                                     HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Instructor Not Found by Username");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTRUCTOR_NOT_FOUND_BY_USERNAME");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(InstructorNotFoundByEmailException.class)
    public ResponseEntity<ProblemDetail> handleInstructorNotFoundByEmailException(InstructorNotFoundByEmailException e,
                                                                                   HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Instructor Not Found by Email");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTRUCTOR_NOT_FOUND_BY_EMAIL");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(InstructorAlreadyExistException.class)
    public ResponseEntity<ProblemDetail> handleInstructorAlreadyExistException(InstructorAlreadyExistException e,
                                                                                 HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Instructor Already Exists");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTRUCTOR_ALREADY_EXISTS");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(problemDetail);
    }

    @ExceptionHandler(InstructorDetailMissingException.class)
    public ResponseEntity<ProblemDetail> handleInstructorDetailMissingException(InstructorDetailMissingException e,
                                                                                 HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Instructor Detail Missing");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTRUCTOR_DETAIL_MISSING");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    @ExceptionHandler(InstructorNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleInstructorNotFoundException(InstructorNotFoundException e,
                                                                             HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Instructor Not Found");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "INSTRUCTOR_NOT_FOUND");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    @ExceptionHandler(ModuleValidationException.class)
    public ResponseEntity<ProblemDetail> handleModuleValidationException(ModuleValidationException e,
                                                                           HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Module Validation Error");
        problemDetail.setDetail(e.getMessage()); // assuming the exception has a message
        problemDetail.setProperty("errorCode", "MODULE_VALIDATION_ERROR");
        problemDetail.setProperty("path", request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }
}
