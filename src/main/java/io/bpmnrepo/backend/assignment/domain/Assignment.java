package io.bpmnrepo.backend.assignment.domain;

import io.bpmnrepo.backend.shared.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Assignment {
    private final String userId;
    private final String bpmnRepositoryId;
    //private AssignmentId assignmentId;
    private RoleEnum roleEnum;

    public Assignment(final String userId, final String bpmnRepositoryId, final RoleEnum roleEnum){
        this.userId = userId;
        this.bpmnRepositoryId = bpmnRepositoryId;
        this.roleEnum = roleEnum;
    }
}
