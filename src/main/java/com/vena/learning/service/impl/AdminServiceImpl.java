package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.AdminApproveCourse;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.CourseStats;
import com.vena.learning.dto.responseDto.CourseStatusResponse;
import com.vena.learning.dto.responseDto.CourseSummaryResponse;
import com.vena.learning.dto.responseDto.EnrollmentSummary;
import com.vena.learning.dto.responseDto.InstructorStatResponse;
import com.vena.learning.dto.responseDto.InstructorStats;
import com.vena.learning.dto.responseDto.ModuleResponse;
import com.vena.learning.dto.responseDto.QuizSummary;
import com.vena.learning.dto.responseDto.RegisterResponse;
import com.vena.learning.dto.responseDto.StatisticsResponse;
import com.vena.learning.dto.responseDto.StudentStatResponse;
import com.vena.learning.dto.responseDto.UserResponse;
import com.vena.learning.exception.customException.AdminException.AdminAlreadyExistsException;
import com.vena.learning.exception.customException.AdminException.AdminNotFoundByEmailException;
import com.vena.learning.exception.customException.AdminException.AdminNotFoundByIdException;
import com.vena.learning.exception.customException.AdminException.AdminNotFoundByUsernameException;
import com.vena.learning.exception.customException.AdminException.IncompleteAdminDetailsException;
import com.vena.learning.exception.customException.CourseExceptions.CourseAlreadyApprovedException;
import com.vena.learning.exception.customException.CourseExceptions.CourseAlreadyDeletedException;
import com.vena.learning.exception.customException.CourseExceptions.CourseApprovalNotAuthorizedException;
import com.vena.learning.exception.customException.CourseExceptions.CourseDeletionNotAuthorizedException;
import com.vena.learning.exception.customException.CourseExceptions.CourseViewNotAuthorizedException;
import com.vena.learning.exception.customException.InstitutionExceptions.InstitutionDetailsMissingException;
import com.vena.learning.exception.customException.InstitutionExceptions.NoCoursesFoundForInstitutionException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundForCourseException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorViewNotAuthorizedException;
import com.vena.learning.exception.customException.StudentExceptions.StudentViewNotAuthorizedException;
import com.vena.learning.exception.customException.UserExceptions.UserDeletionNotAuthorizedException;
import com.vena.learning.model.Course;
import com.vena.learning.model.Enrollment;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.Student;
import com.vena.learning.model.User;
import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.model.Admin;
import com.vena.learning.enums.Role;
import com.vena.learning.repository.AdminRepository;
import com.vena.learning.service.AdminService;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.EnrollmentService;
import com.vena.learning.service.InstructorService;
import com.vena.learning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    private List<UserResponse> mapToUserResponse(List<User> users) {
        return users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public RegisterResponse registerAdmin(RegisterRequest adminRequest) {
        if(adminRequest.getName() == null || adminRequest.getEmail() == null || adminRequest.getUsername() == null || adminRequest.getPassword() == null) {
            throw new IncompleteAdminDetailsException("Admin details are incomplete");
        }
        if(isExists(adminRequest.getEmail(), adminRequest.getUsername())) {
            throw new AdminAlreadyExistsException("Admin already exists with email: " + adminRequest.getEmail() + " or username: " + adminRequest.getUsername());
        }
        return new RegisterResponse(saveAdmin(adminRequest));
    }

    @Override
    public Admin saveAdmin(RegisterRequest adminRequest) {
        Admin admin = new Admin();
        admin.setName(adminRequest.getName());
        admin.setEmail(adminRequest.getEmail());
        admin.setUsername(adminRequest.getUsername());
        admin.setPassword(adminRequest.getPassword());
        admin.setInstitution(adminRequest.getInstitution());
        admin.setRole(Role.ROLE_ADMIN);
        adminRepository.save(admin);
        return admin;
    }

    @Override
    public boolean isExists(String email, String username) {
        return isExistsByEmail(email)|| isExistsByUsername(username);
    }

    @Override
    public boolean isExistsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(
                () -> new AdminNotFoundByEmailException("Admin not found with email: " + email)
        );
    }

    @Override
    public Admin getAdminById(String id) {
        return adminRepository.findById(id).orElseThrow(
                () -> new AdminNotFoundByIdException("Admin not found with id: " + id)
        );
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(
                () -> new AdminNotFoundByUsernameException("Admin not found with username: " + username)
        );
    }

    @Override
    public List<CourseResponse> getAllCoursesByInstitution(String adminId){
        String institution = getInstitutionByAdminId(adminId);
        if(institution==null || institution.trim().isEmpty()){
            throw new InstitutionDetailsMissingException("Institution cannot be null or empty");
        }

        List<Instructor> instructors= instructorService.getAllInstructorByInstitute(institution);
        List<Course> courses = instructors.stream()
                .flatMap(instructor -> instructor.getCourses().stream())
                .collect(Collectors.toList());
        return courses.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllUsersByInstitution(String adminId) {
        String institution = getInstitutionByAdminId(adminId);
        if(institution==null || institution.trim().isEmpty()){
            throw new InstitutionDetailsMissingException("Institution cannot be null or empty");
        }
        List<User> allUsers=new ArrayList<>();
        allUsers.addAll(studentService.getAllStudentByInstitute(institution));
        allUsers.addAll(instructorService.getAllInstructorByInstitute(institution));
        return mapToUserResponse(allUsers);
    }

    @Override
    public String getInstitutionByAdminId(String adminId) {
        Admin admin = getAdminById(adminId);
        return admin.getInstitution();
    }

    @Override
    public void deleteUser(String adminID, String userId) {
        if(studentService.isStudentExist(userId)){
            Student student = studentService.getStudentById(userId);
            if(student.getInstitution().equals(getInstitutionByAdminId(adminID))) {
                // Only delete if the student belongs to the same institution as the admin
                studentService.deleteStudent(userId);
            }else{
                throw new UserDeletionNotAuthorizedException("You are not authorized to delete this user");
            }
        }else if(instructorService.isInstructorExist(userId)) {
            Instructor instructor  = instructorService.getInstructorById(userId);
            if(instructor.getInstitution().equals(getInstitutionByAdminId(adminID))) {
                // Only delete if the instructor belongs to the same institution as the admin
                instructorService.deleteInstructor(userId);
            } else {
                throw new UserDeletionNotAuthorizedException("You are not authorized to delete this user");
            }
        }
        else {
            throw new UserDeletionNotAuthorizedException("You are not authorized to delete this user");
        }
    }

    @Override
    public void deleteCourse(String adminId,String courseId) {
        Course course = courseService.getCourseById(courseId);
        if(course.isDeleted()){
            throw new CourseAlreadyDeletedException("Course already deleted");
        }
        Instructor instructor = course.getInstructor();

        String instructorInstitution = instructor.getInstitution();
        String adminInstitution = getInstitutionByAdminId(adminId);

        if (!instructorInstitution.equalsIgnoreCase(adminInstitution)) {
            throw new CourseDeletionNotAuthorizedException("You are not authorized to delete this course");
        }
        course.setDeleted(true);
        courseService.addCourse(course);
    }

    @Override
    public void approveCourse(AdminApproveCourse adminApproveCourse) {
        String courseId = adminApproveCourse.getCourseId();
        String adminId = adminApproveCourse.getAdminId();

        Course course = courseService.getCourseById(courseId);
        if(course.isApproved()){
            throw new CourseAlreadyApprovedException("Course already approved");
        }
        Instructor instructor = course.getInstructor();
        if (instructor == null) {
            throw new InstructorNotFoundForCourseException("Instructor not found for this course");
        }
        String instructorInstitution = instructor.getInstitution();
        String adminInstitution = getInstitutionByAdminId(adminId);

        if (!instructorInstitution.equalsIgnoreCase(adminInstitution)) {
            throw new CourseApprovalNotAuthorizedException("You are not authorized to approve this course");
        }
        course.setApproved(true);
        courseService.addCourse(course);
    }

    @Override
    public StatisticsResponse getStatistics(String adminId) {
        List<Student> students = studentService.getAllStudents();
        List<Instructor> instructors = instructorService.getAllInstructors();

        List<Course> courses = instructors.stream().flatMap(instructor -> instructor.getCourses().stream())
                .toList();
        List<Enrollment> enrollments = courses.stream().flatMap(course -> course.getEnrollments().stream())
                .toList();

        long totalStudents = students.size();
        long totalInstructors = instructors.size();
        long totalCourses = courses.size();
        long approvedCourses = courses.stream().filter(Course::isApproved).count();
        long pendingCourses = totalCourses - approvedCourses;
        long totalEnrollments = enrollments.size();
        long completedEnrollments = enrollments.stream().filter(Enrollment::getIsCompleted).count();

        List<InstructorStats> instructorStatsList = instructors.stream().map(instructor -> {
            List<Course> instructorCourses = courses.stream()
                    .filter(c -> c.getInstructor() != null && c.getInstructor().getId().equals(instructor.getId()))
                    .collect(Collectors.toList());

            List<CourseStats> courseStats = instructorCourses.stream().map(course -> {
                List<Enrollment> courseEnrollments = enrollments.stream()
                        .filter(e -> e.getCourse().getId().equals(course.getId()))
                        .collect(Collectors.toList());

                int courseCompletedEnrollments = (int) courseEnrollments.stream().filter(Enrollment::getIsCompleted).count();

                int noOfModules = course.getModules() != null ? course.getModules().size() : 0;

                return mapToCourseStats(course.getId(), course.getTitle(), course.isApproved(), courseEnrollments.size(), courseCompletedEnrollments, noOfModules);
            }).toList();

            return mapToInstructorResponse(instructor.getId(), instructor.getName(), instructorCourses.size(), courseStats);
        }).toList();

        return mapToStatisticsResponse(totalStudents, totalInstructors, totalCourses, approvedCourses, pendingCourses, totalEnrollments, completedEnrollments, instructorStatsList);
   }

    private StatisticsResponse mapToStatisticsResponse(long totalStudents, long totalInstructors, long totalCourses, long approvedCourses, long pendingCourses, long totalEnrollments, long completedEnrollments, List<InstructorStats> instructors) {
        StatisticsResponse response = new StatisticsResponse();
        response.setTotalUsers(totalStudents + totalInstructors);
        response.setTotalStudents(totalStudents);
        response.setTotalInstructors(totalInstructors);
        response.setTotalCourses(totalCourses);
        response.setApprovedCourses(approvedCourses);
        response.setPendingCourses(pendingCourses);
        response.setTotalEnrollments(totalEnrollments);
        response.setCompletedEnrollments(completedEnrollments);
        response.setInstructors(instructors);
        return response;
    }

    private InstructorStats mapToInstructorResponse(String instructorId, String instructorName, int totalCourses, List<CourseStats> courseStats) {
        InstructorStats instructorDto = new InstructorStats();
        instructorDto.setInstructorId(instructorId);
        instructorDto.setInstructorName(instructorName);
        instructorDto.setTotalCourses(totalCourses);
        instructorDto.setCourses(courseStats);
        return instructorDto;
    }

    private CourseStats mapToCourseStats(String courseId, String courseName, boolean isApproved, int totalEnrollments, int completedEnrollments, int noOfModules) {
        CourseStats courseDto = new CourseStats();
        courseDto.setCourseId(courseId);
        courseDto.setTitle(courseName);
        courseDto.setApproved(isApproved);
        courseDto.setTotalEnrollments(totalEnrollments);
        courseDto.setCompletedEnrollments(completedEnrollments);
        courseDto.setNoOfModules(noOfModules);
        return courseDto;
    }

    @Override
    public CourseStatusResponse getCoursesByApprovalStatus(String adminId) {
        List<CourseResponse> allCourses = getAllCoursesByInstitution(adminId);

        if (allCourses == null || allCourses.isEmpty()) {
            throw new NoCoursesFoundForInstitutionException("No courses found for the institution");
        }

        Map<String, List<CourseResponse>> coursesByStatus = allCourses.stream()
                .collect(Collectors.groupingBy(
                        course -> course.isApproved() ? "Approved Courses" : "Pending Courses"
                ));

        Map<String, List<CourseSummaryResponse>> summaryMap = new HashMap<>();

        for (Map.Entry<String, List<CourseResponse>> entry : coursesByStatus.entrySet()) {
            List<CourseSummaryResponse> summaries = entry.getValue().stream()
                    .peek(course -> {
                        if (course.getModules() != null) {
                            course.getModules().sort(Comparator.comparingInt(ModuleResponse::getSequence));
                        }
                    })
                    .map(CourseSummaryResponse::new)
                    .collect(Collectors.toList());

            summaryMap.put(entry.getKey(), summaries);
        }

        return new CourseStatusResponse(summaryMap);
    }

    @Override
    public List<UserResponse>
    getAllStudentsByInstitution(String adminId) {
        String institution = getInstitutionByAdminId(adminId);
        if (institution == null || institution.trim().isEmpty()) {
            throw new InstitutionDetailsMissingException("Institution cannot be null or empty");
        }
        List<Student> students = studentService.getAllStudentByInstitute(institution);
        return students.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllInstructorsByInstitution(String adminId) {
        String institution = getInstitutionByAdminId(adminId);
        if (institution == null || institution.trim().isEmpty()) {
            throw new InstitutionDetailsMissingException("Institution cannot be null or empty");
        }
        List<Instructor> instructors = instructorService.getAllInstructorByInstitute(institution);
        return instructors.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public StudentStatResponse getStudentById(String adminId,String studentId) {
        Student student= studentService.getStudentById(studentId);
        if(student.getInstitution().equals(getInstitutionByAdminId(adminId))){
            return mapToShortStudentResponse(student);
        }else {
            throw new StudentViewNotAuthorizedException("Not authorized to view student with id: "+studentId);
        }
    }

    @Override
    public InstructorStatResponse getInstructorById(String adminId, String instructorId) {
        Instructor instructor= instructorService.getInstructorById(instructorId);
        if(instructor.getInstitution().equals(getInstitutionByAdminId(adminId))){
            return new InstructorStatResponse(instructor);
        }else{
            throw new InstructorViewNotAuthorizedException("Not authorized to view instructor with id: " + instructorId);
        }
    }

    @Override
    public CourseStats getCourseById(String adminId, String courseId) {
        Course course = courseService.getCourseById(courseId);
        if(course.getInstructor().getInstitution().equals(getInstitutionByAdminId(adminId))) {
            return new CourseStats(course);
        } else {
            throw new CourseViewNotAuthorizedException("Not authorized to view course with id: " + courseId);
        }
    }

    public StudentStatResponse mapToShortStudentResponse(Student student) {
        StudentStatResponse dto = new StudentStatResponse();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setInstitution(student.getInstitution());

        List<EnrollmentSummary> enrollments = student.getEnrollments().stream()
                .map(EnrollmentSummary::new)
                .toList();
        dto.setEnrollments(enrollments);

        List<QuizSummary> quizAttempts = student.getQuizAttempts().stream()
                .map(QuizSummary::new)
                .toList();
        dto.setQuizAttempts(quizAttempts);

        return dto;
    }

}
