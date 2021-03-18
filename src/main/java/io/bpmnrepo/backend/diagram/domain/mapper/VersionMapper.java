package io.bpmnrepo.backend.diagram.domain.mapper;

import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagramVersion;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface VersionMapper {
    BpmnDiagramVersionEntity toEntity(final BpmnDiagramVersion model);

    @Mapping(target = "bpmnAsXML", expression = "java(new String(entity.getBpmnDiagramVersionFile()))")
    BpmnDiagramVersionTO toTO(final BpmnDiagramVersionEntity entity);

}
