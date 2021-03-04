package io.bpmnrepo.backend.diagram.domain.mapper;

import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagramVersion;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VersionMapper {
    @Mapping(source = "entity", target  = "bpmnDiagramEntity")
    public BpmnDiagramVersionEntity toEntity(final BpmnDiagramVersion model, final BpmnDiagramEntity entity);

    @Mapping(source = "entity.bpmnDiagramEntity.bpmnDiagramId", target = "bpmnDiagramId")
    public BpmnDiagramVersionTO toTO(final BpmnDiagramVersionEntity entity);

}
