package com.vena.learning.repository;

import com.vena.learning.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, String> {
    List<Module> findByCourse_Id(String courseId);

    Optional<Module> findByIdAndCourseId(String moduleId, String courseId);
    List<Module> findByCourseId(String courseId);
}