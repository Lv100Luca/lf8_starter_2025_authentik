package de.szut.lf8_starter.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findByName(String name);
    boolean existsByName(String name);
}
