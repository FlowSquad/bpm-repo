package io.bpmnrepo.backend.repository.domain.mapper;


import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.model.BpmnRepository;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepositoryMapper {
    BpmnRepositoryTO toTO(final BpmnRepositoryEntity entity);

    BpmnRepositoryEntity toEntity(final BpmnRepository model);
}
