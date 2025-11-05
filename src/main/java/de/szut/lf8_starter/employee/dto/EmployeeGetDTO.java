package de.szut.lf8_starter.employee.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmployeeGetDTO {
    private final Long id;
    private final String firstName;
    private final String lastName;
}
