package io.bpmnrepo.backend.repository.domain.mapper;


import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryRequestTO;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.api.transport.NewBpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.model.BpmnRepository;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface RepositoryMapper {
    BpmnRepositoryTO toTO(final BpmnRepositoryEntity entity);

    BpmnRepositoryRequestTO toRequestTO(final BpmnRepositoryEntity entity);

    @Mapping(target = "createdDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedDate", expression = "java(LocalDateTime.now())")
    BpmnRepository toModel(final NewBpmnRepositoryTO to);

    BpmnRepository toModel(final BpmnRepositoryEntity entity);

    BpmnRepositoryEntity toEntity(final BpmnRepository model);
}
