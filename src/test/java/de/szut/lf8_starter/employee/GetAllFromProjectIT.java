package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.projects.ProjectEntity;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class GetAllFromProjectIT extends AbstractIntegrationTest {
    @Test
    @WithMockUser(roles = "user")
    void getAllEmployeesFromProject() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setName("Test Project");
        EmployeeEntity emp1 = new EmployeeEntity();
        emp1.setFirstName("John");
        emp1.setLastName("Doe");
        EmployeeEntity emp2 = new EmployeeEntity();
        emp2.setFirstName("Jane");
        emp2.setLastName("Smith");
        project.getEmployees().add(emp1);
        project.getEmployees().add(emp2);
        projectRepository.save(project);
        employeeRepository.save(emp1);
        employeeRepository.save(emp2);
        this.mockMvc.perform(get("/projects/" + project.getId() + "/employee").with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[1].lastName", is("Smith")));
    }
}

