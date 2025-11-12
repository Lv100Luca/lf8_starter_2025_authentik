package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.projects.dto.ProjectCreateDTO;
import de.szut.lf8_starter.projects.dto.ProjectResponseDTO;
import de.szut.lf8_starter.projects.dto.ProjectUpdateDto;
import de.szut.lf8_starter.projects.mapping.ProjectDtoMapping;
import de.szut.lf8_starter.projects.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectCreateDTO dto) {
        var project = projectService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ProjectDtoMapping.mapToResponseDto(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody ProjectUpdateDto dto) {
        var updatedProject = projectService.updateProject(id, dto);
        return ResponseEntity.ok(ProjectDtoMapping.mapToResponseDto(updatedProject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        var project = projectService.getProjectById(id);
        return ResponseEntity.ok(ProjectDtoMapping.mapToResponseDto(project));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        var projects = projectService.findAll();

        var projectDtos = projects.stream()
                .map(ProjectDtoMapping::mapToResponseDto)
                .toList();

        return ResponseEntity.ok(projectDtos);
    }

    @PostMapping("/{projectId}/assign/{employeeId}")
    public ResponseEntity<Void> AssignEmployeeToProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        projectService.AssignProjectToEmployee(projectId, employeeId);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }
}

