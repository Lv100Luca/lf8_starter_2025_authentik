package de.szut.lf8_starter.projects.service;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.employee.EmployeeRepository;
import de.szut.lf8_starter.projects.ProjectEntity;
import de.szut.lf8_starter.projects.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public List<ProjectEntity> findAll() {
        return projectRepository.findAll();
    }

    public ProjectEntity create(ProjectEntity project) {
        if (projectRepository.existsByName(project.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "project already exists");
        }
        return projectRepository.save(project);
    }

    public ProjectEntity getProjectById(final Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "project not found"));
    }

    public void deleteProjectById(final Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "project not found");
        }
        projectRepository.deleteById(id);
    }

    public ProjectEntity save(final ProjectEntity savedProject) {
        return projectRepository.save(savedProject);
    }

    public List<EmployeeEntity> getAllEmployeesByProject(final Long projectId) {
        ProjectEntity project = getProjectById(projectId);
        return employeeRepository.findAllByProjects(project);
    }
}
