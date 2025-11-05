package de.szut.lf8_starter.projects.mapping;

import de.szut.lf8_starter.projects.ProjectEntity;
import de.szut.lf8_starter.projects.ProjectRepository;
import de.szut.lf8_starter.projects.dto.ProjectCreateDTO;
import de.szut.lf8_starter.projects.dto.ProjectResponseDTO;
import de.szut.lf8_starter.projects.dto.ProjectSimpleGetDTO;
import de.szut.lf8_starter.projects.dto.ProjectUpdateDto;
import de.szut.lf8_starter.projects.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProjectDtoMapping {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    public ProjectResponseDTO getProjectDto(final ProjectEntity project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setProjectManagerId(project.getProjectManagerId());

        return dto;
    }

    public ProjectSimpleGetDTO getProjectSimpleGetDto(final ProjectEntity project) {
        ProjectSimpleGetDTO dto = new ProjectSimpleGetDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setProjectManagerId(project.getProjectManagerId());
        return dto;
    }

    public ProjectResponseDTO updateProject(Long id, ProjectUpdateDto dto) {
        ProjectEntity entity = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "project not found"));
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

    public ProjectEntity createProject(ProjectCreateDTO dto) {
        ProjectEntity entity = ProjectEntity.builder()
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .projectManagerId(dto.getProjectManagerId())
                .build();
        return projectService.create(entity);
    }
}
