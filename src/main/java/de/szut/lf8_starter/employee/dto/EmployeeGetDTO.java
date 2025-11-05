package de.szut.lf8_starter.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeGetDTO {
    private Long id;
    private String firstName;
    private String lastName;
}
