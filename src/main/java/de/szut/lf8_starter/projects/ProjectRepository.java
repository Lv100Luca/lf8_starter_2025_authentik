package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    boolean existsByName(String name);
}
