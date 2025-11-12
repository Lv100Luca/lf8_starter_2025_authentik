package de.szut.lf8_starter.projects.service;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.employee_management.EmployeeManagementService;
import de.szut.lf8_starter.projects.ProjectEntity;
import de.szut.lf8_starter.projects.ProjectRepository;
import de.szut.lf8_starter.projects.dto.ProjectCreateDTO;
import de.szut.lf8_starter.projects.dto.ProjectUpdateDto;
import de.szut.lf8_starter.projects.mapping.ProjectDtoMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeManagementService employeeManagementService;

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

//    public List<ProjectEntity> getProjectsForEmployee(final Long employeeId) {
//        var allProjects = findAll();
//
//        return allProjects.stream().map(p -> p.)
//    }

    public void AssignProjectToEmployee(final Long projectId, final Long employeeId) {
        var employeeOpt = employeeManagementService.getById(employeeId);

        if (employeeOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "employee not found");
        }

        var projectOpt = projectRepository.findById(projectId);

        if (projectOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "project not found");
        }

        var employee = employeeOpt.get();
        var project = projectOpt.get();

        project.getEmployees().add(employee);

        save(project);
    }

    public ProjectEntity save(final ProjectEntity savedProject) {
        return projectRepository.save(savedProject);
    }

    public List<EmployeeEntity> getEmployeesByProject(final Long projectId) {
        ProjectEntity project = getProjectById(projectId);
        return employeeManagementService.findAllByProjects(project);
    }

    public ProjectEntity create(ProjectCreateDTO dto) {
        var entity = ProjectDtoMapping.mapToEntity(dto);

        var managerExists = employeeManagementService.VerifyEmployeeExists(entity.getProjectManagerId());

        if (!managerExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "manager not found");
        }

        return create(entity);
    }

    public ProjectEntity updateProject(Long id, ProjectUpdateDto dto) {
        var existingProject = projectRepository.findById(id);
        if (existingProject.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "project not found");
        }

        var entity = existingProject.get();

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getStartDate() != null) entity.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) entity.setEndDate(dto.getEndDate());
        if (dto.getProjectManagerId() != null) entity.setProjectManagerId(dto.getProjectManagerId());

        save(entity);

        return entity;
    }
}
