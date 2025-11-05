package de.szut.lf8_starter.employee.dto;

import de.szut.lf8_starter.projects.dto.ProjectResponseDTO;
import de.szut.lf8_starter.projects.dto.ProjectSimpleGetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithProjectsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<ProjectSimpleGetDTO> projects;
}

