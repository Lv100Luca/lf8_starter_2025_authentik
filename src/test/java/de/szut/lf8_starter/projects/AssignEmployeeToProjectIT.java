package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AssignEmployeeToProjectIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(post("/projects/1/assign/1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @WithMockUser(roles = "user")
    void assignEmployeeToProject() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setLastName("Test Employee");
        employee = employeeRepository.save(employee);

        ProjectEntity project = new ProjectEntity();
        project.setName("Assign Project");
        project = projectRepository.save(project);

        this.mockMvc.perform(post("/projects/" + project.getId() + "/assign/" + employee.getId())
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}
