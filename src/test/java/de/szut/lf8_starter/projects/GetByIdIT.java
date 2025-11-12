package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetByIdIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/hello")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "user")
    void getById() throws Exception {
        ProjectEntity project1 = new ProjectEntity();
        project1.setName("Project One");
        projectRepository.save(project1);
        this.mockMvc.perform(get("/projects/" + project1.getId()).with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name", org.hamcrest.Matchers.is("Project One")));
    }
}
