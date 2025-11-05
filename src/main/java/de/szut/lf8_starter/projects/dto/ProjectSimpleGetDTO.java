package de.szut.lf8_starter.projects.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectSimpleGetDTO {
    private Long id;
    private String name;
    private Long projectManagerId;
}
