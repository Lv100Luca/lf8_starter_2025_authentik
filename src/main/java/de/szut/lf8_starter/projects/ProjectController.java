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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectDtoMapping projectDtoMapping;

    @PostMapping
    public ResponseEntity<String> createProject(@RequestBody ProjectCreateDTO dto) {
        ProjectEntity project = projectDtoMapping.createProject(dto);
        String request = "Project with id: " + project.getId() + " created, with name: " + project.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody ProjectUpdateDto dto) {
        ProjectResponseDTO updated = projectDtoMapping.updateProject(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        ProjectEntity project = projectService.getProjectById(id);
        ProjectResponseDTO projectResponseDTO = projectDtoMapping.getProjectDto(project);
        return ResponseEntity.ok(projectResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> allProjects = new ArrayList<>();
        for (ProjectEntity project : projectService.findAll()) {
            allProjects.add(projectDtoMapping.getProjectDto(project));
        }
        return ResponseEntity.ok(allProjects);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }
}

