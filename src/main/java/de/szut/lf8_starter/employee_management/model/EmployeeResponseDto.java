package de.szut.lf8_starter.employee_management.model;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponseDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String street;
    private String postcode;
    private String city;
    private String phone;
    private List<SkillDto> skillSet;
}
