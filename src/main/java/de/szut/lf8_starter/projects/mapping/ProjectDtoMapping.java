package de.szut.lf8_starter.projects.mapping;

import de.szut.lf8_starter.projects.ProjectEntity;
import de.szut.lf8_starter.projects.dto.ProjectCreateDTO;
import de.szut.lf8_starter.projects.dto.ProjectResponseDTO;
import de.szut.lf8_starter.projects.dto.ProjectSimpleGetDTO;
import jakarta.persistence.Column;
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

    public static ProjectSimpleGetDTO mapToSimpleDto(final ProjectEntity project) {
        ProjectSimpleGetDTO dto = new ProjectSimpleGetDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setProjectManagerId(project.getProjectManagerId());
        return dto;
    }

    // todo pass project Dto as well
//    public static ProjectResponseDTO updateProject(Long id, ProjectUpdateDto dto) {
//        ProjectEntity entity = projectRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "project not found"));
//        if (dto.getName() != null) entity.setName(dto.getName());
//        if (dto.getStartDate() != null) entity.setStartDate(dto.getStartDate());
//        if (dto.getEndDate() != null) entity.setEndDate(dto.getEndDate());
//        if (dto.getProjectManagerId() != null) entity.setProjectManagerId(dto.getProjectManagerId());
//        entity = projectRepository.save(entity);
//        return new ProjectResponseDTO(
//                entity.getId(),
//                entity.getName(),
//                entity.getStartDate(),
//                entity.getEndDate(),
//                entity.getProjectManagerId()
//        );
//    }

    public static ProjectEntity mapToEntity(ProjectCreateDTO dto) {
        return ProjectEntity.builder()
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .projectManagerId(dto.getProjectManagerId())
                .build();
    }
}
