package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetAllProjectsIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/projects")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void getAllProjects() throws Exception {
        ProjectEntity project1 = new ProjectEntity();
        project1.setName("Project One");
        ProjectEntity project2 = new ProjectEntity();
        project2.setName("Project Two");
        projectRepository.save(project1);
        projectRepository.save(project2);
        this.mockMvc.perform(get("/projects").with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name", is("Project One")))
                .andExpect(jsonPath("$[1].name", is("Project Two")));
    }
}
