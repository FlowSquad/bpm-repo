package io.bpmnrepo.backend.diagram.domain.mapper;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagram;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiagramMapper {
    BpmnDiagram toModel(final BpmnDiagramTO to);

    BpmnDiagramEntity toEntity(final BpmnDiagram model);

    BpmnDiagramTO toTO(final BpmnDiagramEntity entity);
}
