package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.projects.ProjectEntity;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteFromProjectIT extends AbstractIntegrationTest {
    @Transactional
    @Test
    @WithMockUser(roles = "user")
    void deleteEmployeeFromProject() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setName("Test Project");
        projectRepository.save(project);
        EmployeeEntity emp = new EmployeeEntity();
        emp.setFirstName("John");
        emp.setLastName("Doe");
        employeeRepository.save(emp);
        project = projectRepository.findById(project.getId()).orElseThrow();
        emp = employeeRepository.findById(emp.getId()).orElseThrow();
        project.getEmployees().add(emp);
        emp.getProjects().add(project);
        projectRepository.save(project);
        this.mockMvc.perform(delete("/projects/" + project.getId() + "/employee/" + emp.getId()).with(csrf()))
                .andExpect(status().isNoContent());
    }
}
