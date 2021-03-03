package io.bpmnrepo.backend.diagram.domain;

import io.bpmnrepo.backend.diagram.api.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.infrastructure.BpmnDiagramEntity;
import io.bpmnrepo.backend.repository.infrastructure.BpmnRepositoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiagramMapper {
    public BpmnDiagram toModel(final BpmnDiagramTO to);

    @Mapping(source = "entity", target = "bpmnDiagramRepository")
    public BpmnDiagramEntity toEntity(final BpmnDiagram model, final BpmnRepositoryEntity entity);

    @Mapping(source = "bpmnDiagramRepository.bpmnRepositoryId", target = "bpmnDiagramRepositoryId")
    public BpmnDiagramTO toTO(final BpmnDiagramEntity entity);
}
