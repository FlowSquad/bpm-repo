package io.bpmnrepo.backend.diagram.domain.mapper;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagram;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
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
