package io.bpmnrepo.backend.diagram.domain.mapper;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagram;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface DiagramMapper {

    @Mapping(target = "svgPreview", expression = "java((to.getSvgPreview() == null) ? null : to.getSvgPreview().getBytes())")
    BpmnDiagram toModel(final BpmnDiagramTO to);

    BpmnDiagram toModel(final BpmnDiagramEntity entity);

    @Mapping(target = "createdDate", expression="java((model.getCreatedDate() == null) ? LocalDateTime.now() : model.getCreatedDate())")
    @Mapping(target = "updatedDate", expression = "java(LocalDateTime.now())")
    BpmnDiagramEntity toEntity(final BpmnDiagram model);

    @Mapping(target = "svgPreview", expression = "java((entity.getSvgPreview() == null) ? null : new String(entity.getSvgPreview()))")
    BpmnDiagramTO toTO(final BpmnDiagramEntity entity);
}
