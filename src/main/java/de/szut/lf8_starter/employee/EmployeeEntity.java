package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.projects.ProjectEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@Data
@EqualsAndHashCode(exclude = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Builder.Default
    @ManyToMany(mappedBy = "employees")
    private Set<ProjectEntity> projects = new HashSet<>();
}
