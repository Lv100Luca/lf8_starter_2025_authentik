package de.szut.lf8_starter.projects;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateProjectIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void createProject() throws Exception {
        String name = "New Project";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(6);
        Long projectManagerId = 1L;
        final String newProjectJson = String.format("""
                {
                    "name": "%s",
                    "startDate": "%s",
                    "endDate": "%s",
                    "projectManagerId": %s
                }
                """, name, startDate, endDate, projectManagerId);

        final var contentAsString = this.mockMvc.perform(post("/projects").content(newProjectJson).contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name", is("New Project")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var id = Long.parseLong(new JSONObject(contentAsString).get("id").toString());

        final var loadedEntity = projectRepository.findById(id);

        assertThat(loadedEntity).isPresent();
        assertThat(loadedEntity.get().getId()).isEqualTo(id);
        assertThat(loadedEntity.get().getName()).isEqualTo("New Project");
    }
}
