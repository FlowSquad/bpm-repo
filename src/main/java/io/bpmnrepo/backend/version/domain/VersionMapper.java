package io.bpmnrepo.backend.version.domain;

import io.bpmnrepo.backend.diagram.infrastructure.BpmnDiagramEntity;
import io.bpmnrepo.backend.version.api.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.version.infrastructure.BpmnDiagramVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VersionMapper {
    @Mapping(source = "entity", target  = "bpmnDiagramEntity")
    public BpmnDiagramVersionEntity toEntity(final BpmnDiagramVersion model, final BpmnDiagramEntity entity);

    @Mapping(source = "entity.bpmnDiagramEntity.bpmnDiagramId", target = "bpmnDiagramId")
    public BpmnDiagramVersionTO toTO(final BpmnDiagramVersionEntity entity);

}
