package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/projects/{projectId}/employee")
    public ResponseEntity<EmployeeEntity> getAllEmployeesFromProject(@PathVariable Long projectId) {

        //TODO logic

        return ResponseEntity.ok(new EmployeeEntity(projectId));
    }

    @DeleteMapping("/projects/{projectId}/employee/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long projectId, @PathVariable Long employeeId) {

        //TODO logic

        String response = "Employee with id " + employeeId + " was deleted, from project " + projectId + "!";
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
//
//    @PostMapping("projects/{projectId}/employee/{employeeId}")
//    public ResponseEntity<GetProjectDto> addAnExistingPlayerToAProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
//
//        ProjectEntity project = projectService.getProjectById(projectId);
//        EmployeeEntity employee = employeeService.getEmployeeById(employeeId);
//
//        project.getEmployeeList.add(employee);
//        GetProjectDto projectDto = projectMappingService.convert(project);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(projectDto);
//    }
}
