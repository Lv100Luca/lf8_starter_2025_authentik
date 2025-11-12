package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteProjectIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(delete("/projects/1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void deleteProject() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setName("Project to Delete");
        project = projectRepository.save(project);

        this.mockMvc.perform(delete("/projects/" + project.getId()).with(csrf()))
                .andExpect(status().isNoContent());
    }
}
