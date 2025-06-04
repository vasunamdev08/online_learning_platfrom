package com.vena.learning.service.impl;

import com.vena.learning.dto.requestDto.ChoiceRequest;
import com.vena.learning.dto.requestDto.CourseRequest;
import com.vena.learning.dto.requestDto.CreateQuizRequest;
import com.vena.learning.dto.requestDto.ModuleRequest;
import com.vena.learning.dto.requestDto.QuestionRequest;
import com.vena.learning.dto.requestDto.RegisterRequest;
import com.vena.learning.dto.responseDto.CourseResponse;
import com.vena.learning.dto.responseDto.RegisterResponse;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorAlreadyExistException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorCourseOwnershipException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorDetailMissingException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundByEmailException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundByIdException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundByUsernameException;
import com.vena.learning.exception.customException.InstructorExceptions.InstructorNotFoundException;
import com.vena.learning.exception.customException.ModuleExceptions.ModuleValidationException;
import com.vena.learning.model.Choice;
import com.vena.learning.model.Course;
import com.vena.learning.dto.responseDto.ModuleResponse;
import com.vena.learning.model.Instructor;
import com.vena.learning.model.Module;
import com.vena.learning.enums.Role;
import com.vena.learning.model.Question;
import com.vena.learning.model.Quiz;
import com.vena.learning.enums.Type;
import com.vena.learning.repository.InstructorRepository;
import com.vena.learning.repository.QuizRepository;
import com.vena.learning.service.CourseService;
import com.vena.learning.service.InstructorService;
import com.vena.learning.service.ModuleService;
import com.vena.learning.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    @Lazy
    private CourseService courseService;

    @Autowired
    @Lazy
    private ModuleService moduleService;

    @Autowired
    private QuizService quizService;

    @Override
    public Instructor getInstructorById(String id) {
        return instructorRepository.findById(id).orElseThrow(
                () -> new InstructorNotFoundByIdException("Instructor not found with id: " + id)
        );
    }

    @Override
    public Instructor getInstructorByUsername(String username) {
        return instructorRepository.findByUsername(username).orElseThrow(
                () -> new InstructorNotFoundByUsernameException("Instructor not found with username: " + username)
        );
    }

    @Override
    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.getInstructorByEmail(email).orElseThrow(
                () -> new InstructorNotFoundByEmailException("Instructor not found with email: " + email)
        );
    }

    @Override
    public boolean isExist(String email, String username) {
        return instructorRepository.getInstructorByEmail(email).isPresent() || instructorRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean isExistsByUsername(String username) {
        return instructorRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return instructorRepository.existsByEmail(email);
    }

    @Override
    public Instructor saveInstructor(RegisterRequest instructorRequest) {
        Instructor instructor = new Instructor();
        instructor.setName(instructorRequest.getName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setUsername(instructorRequest.getUsername());
        instructor.setPassword(instructorRequest.getPassword());
        instructor.setInstitution(instructorRequest.getInstitution());
        instructor.setRole(Role.ROLE_INSTRUCTOR);
        instructorRepository.save(instructor);
        return instructor;
    }

    @Override
    public RegisterResponse registerInstructor(RegisterRequest instructorRequest) {
        if(isExist(instructorRequest.getEmail(), instructorRequest.getUsername())) {
            throw new InstructorAlreadyExistException("Instructor already exists with email or username");
        }
        if(instructorRequest.getName() == null || instructorRequest.getEmail() == null || instructorRequest.getUsername() == null || instructorRequest.getPassword() == null) {
            throw new InstructorDetailMissingException("Instructor details are incomplete");
        }
        return new RegisterResponse(saveInstructor(instructorRequest));
    }

    @Override
    public List<Instructor> getAllInstructorByInstitute(String institution){
        return instructorRepository.findByInstitution(institution).orElseThrow(()-> new InstructorNotFoundException("Instructor not found"));
    }
    @Override
    public void deleteInstructor(String userId) {
        Instructor instructor = getInstructorById(userId);
        instructorRepository.delete(instructor);
    }

    @Override
    public Optional<Instructor> findById(String userId) {
        return instructorRepository.findById(userId);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
     public  List<CourseResponse> getCoursesByInstructor(String instructorId) {
        if (!instructorRepository.existsById(instructorId)) {
            throw new InstructorNotFoundByIdException("Instructor with ID " + instructorId + " does not exist");
        }
        return courseService.getCoursesByInstructorId(instructorId);
    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        if (!instructorRepository.existsById(request.getInstructorId())) {
            throw new InstructorNotFoundByIdException("Instructor with ID " + request.getInstructorId() + " is not present");
        }
        return courseService.addCourseWithModules(request);
    }

    @Override
    public CourseResponse updateInstructorCourse(CourseRequest request) {
        return courseService.updateCourse(request);
    }

    @Override
    public void deleteInstructorCourse(String courseId) {
        courseService.deleteCourse(courseId);
    }

    @Override
    public CourseResponse addModuleToCourse(CourseRequest courseRequest) {
        return moduleService.addModuleToCourse(courseRequest);
    }

    @Override
    public boolean isInstructorExist(String userId) {
        return instructorRepository.existsById(userId);
    }

    @Override
    public ModuleResponse updateModule(ModuleRequest moduleRequest) {
        if (moduleRequest.getId() == null || moduleRequest.getCourseId() == null) {
            throw new ModuleValidationException("Module ID and Course ID must not be null.");
        }

        // Validate course exists
        Course course = courseService.getCourseById(moduleRequest.getCourseId());

        // Validate module exists
        Module existingModule = moduleService.fetchModuleByIdOrThrow(moduleRequest.getId());

        // Ensure module belongs to the course
        if (!existingModule.getCourse().getId().equals(moduleRequest.getCourseId())) {
            throw new ModuleValidationException("Module does not belong to the specified course.");
        }

        // Fetch all modules for the course
        List<Module> allModules = moduleService.getModulesByCourseId(moduleRequest.getCourseId());

        // Validate based on sequence position
        moduleService.validateModuleTypeBySequence(moduleRequest, allModules);

        // Persist changes
        Module updated = moduleService.updateModule(existingModule, moduleRequest);
        return new ModuleResponse(updated);
    }

    @Override
    public void addQuizToCourse(CreateQuizRequest quizRequest) {
        Course course = courseService.getCourseById(quizRequest.getCourseId());
        Instructor instructor = getInstructorById(quizRequest.getInstructorId());
        if(course.getInstructor()!= instructor) {
            throw new InstructorCourseOwnershipException("Instructor does not own this course");
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getQuizTitle());
        quiz.setCourse(course);

        List<Question> questionEntities = new ArrayList<>();

        for (QuestionRequest questionRequest : quizRequest.getQuestions()) {
            Question question = new Question();
            question.setQuestion(questionRequest.getQuestionText());
            question.setQuiz(quiz); // Set back-reference

            List<Choice> choiceEntities = new ArrayList<>();
            for (ChoiceRequest choiceRequest : questionRequest.getChoices()) {
                Choice choice = new Choice();
                choice.setOptionText(choiceRequest.getChoiceText());
                choice.setCorrect(choiceRequest.isCorrect());
                choice.setQuestion(question); // Set back-reference
                choiceEntities.add(choice);
            }

            question.setChoices(choiceEntities);
            questionEntities.add(question);
        }

        quiz.setQuestions(questionEntities);
        quizRepository.save(quiz);  // Cascade saves everything
    }


    @Override
    public void deleteModule(String moduleId) {
        // Fetch the module
        Module module = moduleService.fetchModuleByIdOrThrow(moduleId);

        // Only allow deletion if the type is LESSON
        if (module.getType() != Type.Lesson) {
            throw new RuntimeException("Cannot delete module of type " + module.getType() + ". Only LESSON modules can be deleted.");
        }

        // Get the courseId from the module
        String courseId = module.getCourse().getId();

        // Delete the module
        moduleService.deleteModuleById(moduleId);

        // Fetch remaining modules for the course
        List<Module> remainingModules = moduleService.getModulesByCourseId(courseId);

        // Sort by current sequence
        remainingModules.sort(Comparator.comparingInt(Module::getSequence));

        // Reassign sequence numbers starting from 1
        int newSequence = 1;
        for (Module m : remainingModules) {
            m.setSequence(newSequence++);
        }

        // Save updated modules
        moduleService.saveAllModules(remainingModules);
    }

}
