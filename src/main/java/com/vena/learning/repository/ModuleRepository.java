package com.vena.learning.repository;

import com.vena.learning.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module,String> {
}
