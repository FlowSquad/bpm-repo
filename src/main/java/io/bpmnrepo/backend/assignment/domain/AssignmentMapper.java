package io.bpmnrepo.backend.assignment.domain;

import io.bpmnrepo.backend.assignment.infrastructure.AssignmentEntity;
import io.bpmnrepo.backend.assignment.infrastructure.AssignmentId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    @Mapping(source = "assignmentId", target = "assignmentId")
    public AssignmentEntity toEntity(Assignment model, AssignmentId assignmentId);

    public AssignmentId toEmbeddable(String userId, String bpmnRepositoryId);
}
