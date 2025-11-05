package de.szut.lf8_starter.employee.service;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeEntity getEmployeeById(final Long employeeId) {
        return employeeRepository.findEmployeeEntitiesById(employeeId);
    }
}
