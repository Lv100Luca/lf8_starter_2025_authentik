package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.projects.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findAllByProjects(ProjectEntity project);
}
