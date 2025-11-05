package de.szut.lf8_starter.employee.mapping;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.employee.dto.EmployeeGetDTO;
import de.szut.lf8_starter.employee.dto.EmployeeWithProjectsDTO;
import de.szut.lf8_starter.projects.dto.ProjectSimpleGetDTO;
import de.szut.lf8_starter.projects.mapping.ProjectDtoMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeDtoMapping {

    private final ProjectDtoMapping projectDtoMapping;

    public EmployeeGetDTO toEmployeeGetDTO(EmployeeEntity employee) {
        return new EmployeeGetDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName()
        );
    }

    public EmployeeWithProjectsDTO toEmployeeWithProjectsDTO(EmployeeEntity employee) {
        var resultDto = new EmployeeWithProjectsDTO();
        resultDto.setId(employee.getId());
        resultDto.setFirstName(employee.getFirstName());
        resultDto.setLastName(employee.getLastName());

        var projects = new HashSet<ProjectSimpleGetDTO>();

        employee.getProjects().forEach(project -> {
            projects.add(projectDtoMapping.getProjectSimpleGetDto(project));
        });

        resultDto.setProjects(projects);
        return resultDto;
    }
}
