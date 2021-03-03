package io.bpmnrepo.backend.assignment.infrastructure;

import io.bpmnrepo.backend.shared.RoleEnum;
import lombok.*;

import javax.persistence.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "assignment")
public class AssignmentEntity {

    @EmbeddedId
    private AssignmentId assignmentId;

    //0: OWNER - 1:ADMIN - 2:MEMBER - 3:VIEWER
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_enum")
    private RoleEnum roleEnum;
}
