package io.miragon.bpmrepo.core.artifact.infrastructure.repository;

import io.miragon.bpmrepo.core.artifact.infrastructure.entity.SharedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharedJpaRepository extends JpaRepository<SharedEntity, String> {
    Optional<SharedEntity> findById_ArtifactIdAndId_RepositoryId(final String artifactId, final String repositoryId);

    int deleteById_ArtifactIdAndId_RepositoryId(final String artifactId, final String repositoryId);

    int deleteById_ArtifactIdAndId_TeamId(final String artifactId, final String teamId);

}
