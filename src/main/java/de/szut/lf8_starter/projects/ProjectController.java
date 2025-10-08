package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.projects.dto.ProjectCreateDTO;
import de.szut.lf8_starter.projects.dto.ProjectResponseDTO;
import de.szut.lf8_starter.projects.dto.ProjectUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Long> createProject(@RequestBody ProjectCreateDTO dto) {
        Long id = projectService.createProject(dto);
        return ResponseEntity.created(URI.create("/projects/" + id)).body(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody ProjectUpdateDto dto) {
        ProjectResponseDTO updated = projectService.updateProject(id, dto);
        return ResponseEntity.ok(updated);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }
}

