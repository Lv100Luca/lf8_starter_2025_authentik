package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.projects.dto.ProjectCreateDTO;
import de.szut.lf8_starter.projects.dto.ProjectResponseDTO;
import de.szut.lf8_starter.projects.dto.ProjectUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final RestTemplate restTemplate;
    private final String employeeServiceUrl = "http://employee-service/api/employees/";

    @Transactional
    public Long createProject(ProjectCreateDTO dto) {
        if (!employeeExists(dto.getProjectManagerId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projektleiter nicht gefunden");
        }
        if (projectRepository.existsByName(dto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Projekt existiert bereits");
        }
        ProjectEntity entity = ProjectEntity.builder()
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .projectManagerId(dto.getProjectManagerId())
                .build();
        entity = projectRepository.save(entity);
        return entity.getId();
    }

    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projekt nicht gefunden");
        }
        projectRepository.deleteById(id);
    }

    @Transactional
    public ProjectResponseDTO updateProject(Long id, ProjectUpdateDto dto) {
        ProjectEntity entity = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projekt nicht gefunden"));
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getStartDate() != null) entity.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) entity.setEndDate(dto.getEndDate());
        if (dto.getProjectManagerId() != null) entity.setProjectManagerId(dto.getProjectManagerId());
        entity = projectRepository.save(entity);
        return new ProjectResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getProjectManagerId()
        );
    }

    private boolean employeeExists(Long employeeId) {
        try {
            restTemplate.getForEntity(employeeServiceUrl + employeeId, Void.class);
            return true;
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
