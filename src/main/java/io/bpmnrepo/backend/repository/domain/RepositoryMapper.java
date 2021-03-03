package io.bpmnrepo.backend.repository.domain;


import io.bpmnrepo.backend.repository.api.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.infrastructure.BpmnRepositoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepositoryMapper {
    public BpmnRepositoryTO toTO(final BpmnRepositoryEntity entity);
    public BpmnRepositoryEntity toEntity(final BpmnRepository model);
}
