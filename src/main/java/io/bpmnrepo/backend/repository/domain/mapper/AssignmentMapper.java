package io.bpmnrepo.backend.repository.domain.mapper;

import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentId;
import io.bpmnrepo.backend.repository.domain.model.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    AssignmentEntity toEntity(Assignment model, AssignmentId assignmentId);

    AssignmentId toEmbeddable(String userId, String bpmnRepositoryId);
}
