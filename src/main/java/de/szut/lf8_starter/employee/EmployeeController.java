package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeGetDTO;
import de.szut.lf8_starter.employee.mapping.EmployeeDtoMapping;
import de.szut.lf8_starter.projects.ProjectEntity;
import de.szut.lf8_starter.projects.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeDtoMapping employeeDtoMapping;
    private final ProjectService projectService;

    @GetMapping("/projects/{projectId}/employee")
    public ResponseEntity<List<EmployeeGetDTO>> getAllEmployeesFromProject(@PathVariable Long projectId) {
        List<EmployeeGetDTO> employeeGetDTOList = new ArrayList<>();
        List<EmployeeEntity> employeeList = projectService.getAllEmployeesByProject(projectId);

        for (EmployeeEntity employee : employeeList) {
            employeeGetDTOList.add(employeeDtoMapping.toEmployeeGetDTO(employee));
        }

        return ResponseEntity.ok(employeeGetDTOList);
    }

    @DeleteMapping("/projects/{projectId}/employee/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long projectId, @PathVariable Long employeeId) {
        ProjectEntity project = projectService.getProjectById(projectId);
        EmployeeEntity employee = project.getEmployees().stream()
            .filter(e -> e.getId().equals(employeeId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Employee not found in project"));

        project.getEmployees().remove(employee);
        projectService.save(project);

        String response = "Employee " + employee.getLastName() + " with id " + employeeId + " was deleted from project " + projectId + "!";
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
