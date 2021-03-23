package io.bpmnrepo.backend.repository;

import io.bpmnrepo.backend.repository.domain.model.Assignment;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentId;
import io.bpmnrepo.backend.shared.enums.RoleEnum;

public class AssignmentBuilder {

    public static AssignmentEntity buildAssignment(final AssignmentId assignmentId, final RoleEnum roleEnum){
        return AssignmentEntity.builder()
                .assignmentId(assignmentId)
                .roleEnum(roleEnum)
                .build();
    }

    public static AssignmentId buildAssignmentId(String userId, String bpmnRepositoryId){
        return AssignmentId.builder()
                .userId(userId)
                .bpmnRepositoryId(bpmnRepositoryId)
                .build();
    }

}
