package de.szut.lf8_starter.projects.mapping;

import de.szut.lf8_starter.projects.ProjectEntity;
import de.szut.lf8_starter.projects.dto.ProjectCreateDTO;
import de.szut.lf8_starter.projects.dto.ProjectResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoMapping {
    public static ProjectResponseDTO mapToResponseDto(final ProjectEntity project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setProjectManagerId(project.getProjectManagerId());

        return dto;
    }

    public static ProjectEntity mapToEntity(ProjectCreateDTO dto) {
        return ProjectEntity.builder()
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .projectManagerId(dto.getProjectManagerId())
                .build();
    }
}
