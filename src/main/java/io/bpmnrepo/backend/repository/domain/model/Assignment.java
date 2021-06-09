package io.bpmnrepo.backend.repository.domain.model;

import io.bpmnrepo.backend.repository.api.transport.AssignmentTO;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Assignment {
    private String userId;
    private String userName;
    private String bpmnRepositoryId;
    private RoleEnum roleEnum;

    public Assignment(final AssignmentTO assignmentTO){
        this.userId = assignmentTO.getUserId();
        this.userName = assignmentTO.getUserName();
        this.bpmnRepositoryId = assignmentTO.getBpmnRepositoryId();
        this.roleEnum = assignmentTO.getRoleEnum();
    }
}
