package de.szut.lf8_starter.employee_management;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.employee.EmployeeRepository;
import de.szut.lf8_starter.employee_management.model.SkillDto;
import de.szut.lf8_starter.projects.ProjectEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeManagementService {
    private final EmployeeManagementApiClient apiClient;
    private final EmployeeRepository employeeRepository;

    public boolean VerifyEmployeeExists(Long id) {
        return apiClient.getEmployeeById(id).isPresent();
    }

    public List<EmployeeEntity> findAllByProjects(ProjectEntity project) {
        return employeeRepository.findAllByProjects(project);
    }

    public Optional<EmployeeEntity> getById(Long employeeId) {
        return  employeeRepository.findById(employeeId);
    }
}
