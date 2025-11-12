package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetByIdIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/hello")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void getById() throws Exception {
        projectRepository.save(new ProjectEntity(1L, "Project One", null, null, null, null));
        projectRepository.save(new ProjectEntity(2L, "Project Two", null, null, null, null));
        this.mockMvc.perform(get("/projects/1")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Project One")));

        this.mockMvc.perform(get("/projects/2")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Project Two")));
    }
}
