package de.szut.lf8_starter.employee.service;

import de.szut.lf8_starter.employee.EmployeeRepository;
import de.szut.lf8_starter.employee.external.EmployeeApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeApiClient employeeApiClient;

    public String getAllEmployees(String bearerToken) {
        return employeeApiClient.getAllEmployees(bearerToken);
    }

    public String getEmployeeById(Long id, String bearerToken) {
        return employeeApiClient.getEmployeeById(id, bearerToken);
    }
}
