package io.miragon.bpmrepo.core.diagram.domain.mapper;

import io.miragon.bpmrepo.core.diagram.domain.model.Deployment;
import io.miragon.bpmrepo.core.diagram.domain.model.DiagramVersion;
import io.miragon.bpmrepo.core.diagram.infrastructure.entity.DeploymentEntity;
import io.miragon.bpmrepo.core.diagram.infrastructure.entity.DiagramVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface VersionMapper {

    DiagramVersionEntity mapToEntity(final DiagramVersion model);

    @Mapping(target = "deployments", expression = "java(toModel(entity.getDeployments()))")
    DiagramVersion mapToModel(final DiagramVersionEntity entity);

    List<DiagramVersion> mapToModel(List<DiagramVersionEntity> list);

    List<Deployment> toModel(List<DeploymentEntity> a);
}
