package de.szut.lf8_starter.employee.mapping;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.employee.dto.EmployeeGetDTO;
import de.szut.lf8_starter.employee.dto.EmployeeWithProjectsDTO;
import de.szut.lf8_starter.projects.dto.ProjectSimpleGetDTO;
import de.szut.lf8_starter.projects.mapping.ProjectDtoMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class EmployeeDtoMapping {

    public EmployeeGetDTO toEmployeeGetDTO(EmployeeEntity employee) {
        return new EmployeeGetDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName()
        );
    }
}
