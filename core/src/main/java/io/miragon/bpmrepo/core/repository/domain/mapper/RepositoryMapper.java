package io.miragon.bpmrepo.core.repository.domain.mapper;

import io.miragon.bpmrepo.core.artifact.domain.model.Artifact;
import io.miragon.bpmrepo.core.artifact.infrastructure.entity.ArtifactEntity;
import io.miragon.bpmrepo.core.repository.domain.model.Repository;
import io.miragon.bpmrepo.core.repository.infrastructure.entity.RepositoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(imports = LocalDateTime.class)
public interface RepositoryMapper {

    @Mapping(target = "sharedArtifacts", expression = "java(toModel(entity.getSharedArtifacts()))")
    Repository mapToModel(final RepositoryEntity entity);

    RepositoryEntity mapToEntity(final Repository model);

    List<Artifact> toModel(List<ArtifactEntity> artifacts);
}
