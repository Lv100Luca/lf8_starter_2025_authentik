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
    
    public boolean VerifyEmployeeIsQualifiedForProject(int id, ProjectEntity projectEntiy) {
        return false; //todo: implement this with `ProjectEntity`
    }

    public boolean VerifyEmployeeExists(Long id) {
        return apiClient.getEmployeeById(id).isPresent();
    }

    public boolean VerifyEmployeeIsQualified(Long id, Long... qualifications) {
        if (qualifications == null || qualifications.length == 0) {
            return false;
        }

        var employeeOpt = apiClient.getEmployeeById(id);

        if (employeeOpt.isEmpty()) {
            return false;
        }

        var employee = employeeOpt.get();

        var skills = employee.getSkillSet();

        if (skills == null) {
            return false;
        }

        return Arrays.stream(qualifications)
                .allMatch(q -> skills.stream()
                        .map(SkillDto::getId)
                        .anyMatch(skillId -> skillId == q));
    }

    public List<EmployeeEntity> findAllByProjects(ProjectEntity project) {
        return employeeRepository.findAllByProjects(project);
    }

    public Optional<EmployeeEntity> getById(Long employeeId) {
        return  employeeRepository.findById(employeeId);
    }
}
