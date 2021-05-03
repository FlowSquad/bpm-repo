package io.bpmnrepo.backend.diagram.domain.mapper;

import io.bpmnrepo.backend.diagram.domain.model.Starred;
import io.bpmnrepo.backend.diagram.infrastructure.entity.StarredEntity;
import io.bpmnrepo.backend.diagram.infrastructure.entity.StarredId;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface StarredMapper {

    StarredEntity toEntity(final StarredId starredId);

    StarredId toEmbeddable(final String bpmnDiagramId, final String userId);

}
