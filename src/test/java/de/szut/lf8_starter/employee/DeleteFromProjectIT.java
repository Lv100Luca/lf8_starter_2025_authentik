package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.Lf8StarterApplication;
import de.szut.lf8_starter.projects.ProjectEntity;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Lf8StarterApplication.class)
@Transactional
class DeleteFromProjectIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void deleteEmployeeFromProject() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setName("Test Project");
        EmployeeEntity emp = new EmployeeEntity();
        emp.setFirstName("John");
        emp.setLastName("Doe");
        project.getEmployees().add(emp);
        projectRepository.save(project);
        employeeRepository.save(emp);
        this.mockMvc.perform(delete("/projects/" + project.getId() + "/employee/" + emp.getId()).with(csrf()))
                .andExpect(status().isNoContent());
    }
}

