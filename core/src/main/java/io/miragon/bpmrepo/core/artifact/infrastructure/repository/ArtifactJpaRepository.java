package io.miragon.bpmrepo.core.artifact.infrastructure.repository;

import io.miragon.bpmrepo.core.artifact.infrastructure.entity.ArtifactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtifactJpaRepository extends JpaRepository<ArtifactEntity, String> {

    List<ArtifactEntity> findAllByRepositoryIdOrderByUpdatedDateDesc(String bpmnArtifactRepositoryId);

    int countAllByRepositoryId(String bpmnRepositoryId);

    int deleteAllByRepositoryId(String bpmnRepositoryId);

    List<ArtifactEntity> findAllByRepositoryIdInAndNameStartsWithIgnoreCase(List<String> bpmnRepositoryIds, String title);

    Optional<List<ArtifactEntity>> findAllByRepositoryIdAndFileTypeIgnoreCase(String repositoryId, String type);

    Optional<List<ArtifactEntity>> findAllByIdIn(List<String> artifactIds);

    Optional<List<ArtifactEntity>> findAllByRepositoryIdIn(List<String> repositoryIds);

    ArtifactEntity getOne(String id);
}
