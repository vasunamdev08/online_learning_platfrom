package com.vena.learning.dataSeeder;

import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.requestDto.EnrollmentRequest;
import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.enums.Type;
import com.vena.learning.model.*;
import com.vena.learning.model.Module;
import com.vena.learning.repository.*; // Import repositories
import com.vena.learning.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Added for Optional return types

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private AdminService adminService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private ModuleService moduleService; // Now injected

    // Injected Repositories for Quiz-related entities
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ChoiceRepository choiceRepository;
    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Starting database seeding...");

        // Optional: Clear existing data for fresh seeding during development
        // Be very careful with this in production!
        // This requires cascading deletes to be configured correctly in your entities,
        // or you need to delete in the correct order (children first).
        // For simplicity, let's just proceed with seeding, assuming a clean slate or
        // that your services handle existence checks.
        // clearDatabase(); // Uncomment if you have a reliable clear method

        seedUsers();
        seedLearningContent();

        System.out.println("Database seeding completed.");
    }

    // private void clearDatabase() {
    //     quizAttemptRepository.deleteAll();
    //     enrollmentRepository.deleteAll();
    //     choiceRepository.deleteAll();
    //     questionRepository.deleteAll();
    //     quizRepository.deleteAll();
    //     moduleRepository.deleteAll();
    //     courseRepository.deleteAll();
    //     // You would need to add deletion for User and its subclasses too,
    //     // making sure to delete subclasses (Student, Instructor, Admin) before User.
    //     // studentRepository.deleteAll();
    //     // instructorRepository.deleteAll();
    //     // adminRepository.deleteAll();
    //     // userRepository.deleteAll();
    //     System.out.println("Database cleared.");
    // }

    private void seedUsers() {
        System.out.println("Seeding users (Admin, Instructor, Student)...");

        if (!adminService.isExistsByEmail("admin@vena.com")) {
            RegisterRequest adminReq = new RegisterRequest();
            adminReq.setUsername("admin_vena");
            adminReq.setPassword("adminpass");
            adminReq.setName("Vena Admin");
            adminReq.setEmail("admin@vena.com");
            adminReq.setInstitution("Vena Institute");
            adminService.registerAdmin(adminReq);
            System.out.println("Admin 'admin_vena' registered.");
        }

        if (!instructorService.isExistsByEmail("john.doe@vena.com")) {
            RegisterRequest instReq1 = new RegisterRequest();
            instReq1.setUsername("john_doe");
            instReq1.setPassword("instructorpass");
            instReq1.setName("John Doe");
            instReq1.setEmail("john.doe@vena.com");
            instReq1.setInstitution("Vena Institute");
            instructorService.registerInstructor(instReq1);
            System.out.println("Instructor 'john_doe' registered.");
        }

        if (!instructorService.isExistsByEmail("jane.smith@vena.com")) {
            RegisterRequest instReq2 = new RegisterRequest();
            instReq2.setUsername("jane_smith");
            instReq2.setPassword("instructorpass");
            instReq2.setName("Jane Smith");
            instReq2.setEmail("jane.smith@vena.com");
            instReq2.setInstitution("Vena Institute");
            instructorService.registerInstructor(instReq2);
            System.out.println("Instructor 'jane_smith' registered.");
        }

        if (!studentService.isExistsByEmail("alice.student@vena.com")) {
            RegisterRequest studentReq1 = new RegisterRequest();
            studentReq1.setUsername("alice_student");
            studentReq1.setPassword("studentpass");
            studentReq1.setName("Alice Wonderland");
            studentReq1.setEmail("alice.student@vena.com");
            studentReq1.setInstitution("Vena Institute");
            studentService.registerStudent(studentReq1);
            System.out.println("Student 'alice_student' registered.");
        }

        if (!studentService.isExistsByEmail("bob.student@vena.com")) {
            RegisterRequest studentReq2 = new RegisterRequest();
            studentReq2.setUsername("bob_student");
            studentReq2.setPassword("studentpass");
            studentReq2.setName("Bob Builder");
            studentReq2.setEmail("bob.student@vena.com");
            studentReq2.setInstitution("Vena Institute");
            studentService.registerStudent(studentReq2);
            System.out.println("Student 'bob_student' registered.");
        }

        System.out.println("User seeding completed.");
    }

    private void seedLearningContent() {
        System.out.println("Seeding learning content...");

        Instructor inst1 = instructorService.getInstructorByEmail("john.doe@vena.com");
        Student student1 = studentService.getStudentByEmail("alice.student@vena.com");
        Student student2 = studentService.getStudentByEmail("bob.student@vena.com");

        if (inst1 == null || student1 == null || student2 == null) {
            System.err.println("Required users not found. Skipping learning content seeding.");
            return;
        }

        // --- Create Course 1: Introduction to Programming ---
        CourseRequest courseReq1 = new CourseRequest();
        courseReq1.setTitle("Introduction to Java Programming");
        courseReq1.setDescription("A beginner-friendly course on Java fundamentals, including basic syntax, control structures, and object-oriented concepts.");
        courseReq1.setInstructorId(inst1.getId());

        List<ModuleRequest> modules1 = new ArrayList<>();
        modules1.add(createModuleRequest("Welcome to Java Programming", "Course overview and prerequisites.", Type.Introduction, 1));
        modules1.add(createModuleRequest("Java Basics: Variables and Data Types", "Understanding primitive and reference data types.", Type.Lesson, 2));
        modules1.add(createModuleRequest("Control Flow Statements in Java", "If-else, switch, for, while loops.", Type.Lesson, 3));
        modules1.add(createModuleRequest("Object-Oriented Programming Concepts", "Basics of OOP: classes, objects, methods, constructors.", Type.Lesson, 4));
        modules1.add(createModuleRequest("First Java Quiz", "A short quiz to test basic Java knowledge.", Type.Lesson, 5)); // This module will be linked to a quiz
        modules1.add(createModuleRequest("Concluding Java Programming", "Summary of learned concepts and next steps.", Type.Conclusion, 6));
        courseReq1.setModules(modules1);

        CourseResponse course1 = courseService.addCourseWithModules(courseReq1);
        System.out.println("Course 'Introduction to Java Programming' created.");

        // --- Create Course 2: Advanced Spring Boot ---
        CourseRequest courseReq2 = new CourseRequest();
        courseReq2.setTitle("Advanced Spring Boot");
        courseReq2.setDescription("Dive deep into Spring Boot features, including microservices, security, and cloud deployment.");
        courseReq2.setInstructorId(inst1.getId());

        List<ModuleRequest> modules2 = new ArrayList<>();
        modules2.add(createModuleRequest("Introduction to Advanced Spring Boot", "Overview of advanced topics.", Type.Introduction, 1));
        modules2.add(createModuleRequest("Spring Boot Fundamentals & Actuator", "Auto-configuration, Starters, Actuator.", Type.Lesson, 2));
        modules2.add(createModuleRequest("Data Persistence with Spring Data JPA", "Using JPA with Spring Boot, repositories.", Type.Lesson, 3));
        modules2.add(createModuleRequest("Spring Security Integration", "Authentication and Authorization.", Type.Lesson, 4));
        modules2.add(createModuleRequest("Conclusion: Building Production-Ready Applications", "Deployment and best practices.", Type.Conclusion, 5));
        courseReq2.setModules(modules2);

        CourseResponse course2 = courseService.addCourseWithModules(courseReq2);
        System.out.println("Course 'Advanced Spring Boot' created.");


        // --- Create Enrollments ---
        EnrollmentRequest enrollmentReq1 = new EnrollmentRequest();
        enrollmentReq1.setStudentId(student1.getId());
        enrollmentReq1.setCourseId(course1.getId());
        enrollmentService.enrollStudent(enrollmentReq1);
        System.out.println("Student 'Alice Wonderland' enrolled in 'Introduction to Java Programming'.");

        EnrollmentRequest enrollmentReq2 = new EnrollmentRequest();
        enrollmentReq2.setStudentId(student2.getId());
        enrollmentReq2.setCourseId(course1.getId());
        enrollmentService.enrollStudent(enrollmentReq2);
        System.out.println("Student 'Bob Builder' enrolled in 'Introduction to Java Programming'.");

        EnrollmentRequest enrollmentReq3 = new EnrollmentRequest();
        enrollmentReq3.setStudentId(student1.getId());
        enrollmentReq3.setCourseId(course2.getId());
        enrollmentService.enrollStudent(enrollmentReq3);
        System.out.println("Student 'Alice Wonderland' enrolled in 'Advanced Spring Boot'.");

        // --- Seed Quizzes, Questions, Choices, and Quiz Attempts ---
        // Find the quiz module from course1
        // Assuming your CourseServiceImpl returns a Course object that includes its modules
        // and that modules are persisted at this point.
        // You might need to fetch the course again if modules are lazy loaded or not
        // fully populated in the returned Course object.
        Course retrievedCourse1Optional = courseService.getCourseById(course1.getId()); // Assuming findById exists in CourseService

        if (retrievedCourse1Optional != null) {
            Optional<Module> quizModuleOptional = retrievedCourse1Optional.getModules().stream()
                    .filter(m -> m.getTitle().equals("First Java Quiz"))
                    .findFirst();

            if (quizModuleOptional.isPresent()) {
                Module quizModule = quizModuleOptional.get();

                // Create a Quiz
                Quiz quiz1_1 = new Quiz();
                quiz1_1.setTitle(quizModule.getTitle());
                quiz1_1.setCourse(retrievedCourse1Optional);
                quizRepository.save(quiz1_1);
                System.out.println("Quiz '" + quiz1_1.getTitle() + "' created.");

                // Create Questions for Quiz 1_1
                Question q1_1_1 = new Question();
                q1_1_1.setQuestion("Which keyword is used to define a class in Java?");
                q1_1_1.setQuiz(quiz1_1);
                questionRepository.save(q1_1_1);

                Question q1_1_2 = new Question();
                q1_1_2.setQuestion("What is the entry point for a Java application?");
                q1_1_2.setQuiz(quiz1_1);
                questionRepository.save(q1_1_2);
                System.out.println("Questions for Quiz 1_1 created.");

                // Create Choices for Question 1_1_1
                Choice c1_1_1_1 = new Choice();
                c1_1_1_1.setOptionText("class");
                c1_1_1_1.setCorrect(true);
                c1_1_1_1.setQuestion(q1_1_1);
                choiceRepository.save(c1_1_1_1);

                Choice c1_1_1_2 = new Choice();
                c1_1_1_2.setOptionText("Class");
                c1_1_1_2.setCorrect(false);
                c1_1_1_2.setQuestion(q1_1_1);
                choiceRepository.save(c1_1_1_2);

                // FIX: Use a mutable list
                List<Choice> choicesForQ1_1_1 = new ArrayList<>();
                choicesForQ1_1_1.add(c1_1_1_1);
                choicesForQ1_1_1.add(c1_1_1_2);
                q1_1_1.setChoices(choicesForQ1_1_1); // Corrected line
                questionRepository.save(q1_1_1); // Update question to link choices
                System.out.println("Choices for Question 1_1_1 created and linked.");


                // Create Choices for Question 1_1_2
                Choice c1_1_2_1 = new Choice();
                c1_1_2_1.setOptionText("main() method");
                c1_1_2_1.setCorrect(true);
                c1_1_2_1.setQuestion(q1_1_2);
                choiceRepository.save(c1_1_2_1);

                Choice c1_1_2_2 = new Choice();
                c1_1_2_2.setOptionText("start() method");
                c1_1_2_2.setCorrect(false);
                c1_1_2_2.setQuestion(q1_1_2);
                choiceRepository.save(c1_1_2_2);

                // FIX: Use a mutable list
                List<Choice> choicesForQ1_1_2 = new ArrayList<>();
                choicesForQ1_1_2.add(c1_1_2_1);
                choicesForQ1_1_2.add(c1_1_2_2);
                q1_1_2.setChoices(choicesForQ1_1_2); // Corrected line
                questionRepository.save(q1_1_2); // Update question to link choices
                System.out.println("Choices for Question 1_1_2 created and linked.");


                // FIX: Use a mutable list for questions as well
                List<Question> questionsForQuiz = new ArrayList<>();
                questionsForQuiz.add(q1_1_1);
                questionsForQuiz.add(q1_1_2);
                quiz1_1.setQuestions(questionsForQuiz); // Corrected line
                quizRepository.save(quiz1_1); // Update quiz to link questions
                System.out.println("Quiz 1_1 updated with questions.");

                // Seed a quiz attempt for student1 on quiz1_1
                QuizAttempt qa1 = new QuizAttempt();
                qa1.setStudent(student1);
                qa1.setQuiz(quiz1_1);
                qa1.setAttemptDate(LocalDateTime.now());
                qa1.setScore(1); // Student 1 answered one question correctly
                qa1.setAttemptNumber(1);
                quizAttemptRepository.save(qa1);
                System.out.println("Quiz attempt for Alice on Quiz 1_1 created.");

                // Seed a quiz attempt for student2 on quiz1_1
                QuizAttempt qa2 = new QuizAttempt();
                qa2.setStudent(student2);
                qa2.setQuiz(quiz1_1);
                qa2.setAttemptDate(LocalDateTime.now());
                qa2.setScore(2); // Student 2 answered both questions correctly
                qa2.setAttemptNumber(1);
                quizAttemptRepository.save(qa2);
                System.out.println("Quiz attempt for Bob on Quiz 1_1 created.");

            } else {
                System.err.println("Quiz module 'First Java Quiz' not found in retrieved Course 1.");
            }
        } else {
            System.err.println("Failed to retrieve Course 1 for quiz seeding.");
        }

        System.out.println("Learning content seeding completed.");
    }

    private ModuleRequest createModuleRequest(String title, String content, Type type, int sequence) {
        ModuleRequest mr = new ModuleRequest();
        mr.setTitle(title);
        mr.setContent(content);
        mr.setType(type);
        mr.setSequence(sequence);
        return mr;
    }
}