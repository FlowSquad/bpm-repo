package io.bpmnrepo.backend.assignment.api;


import io.bpmnrepo.backend.shared.RoleEnum;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentTO {

    @NotNull
    private String bpmnRepositoryId;

    @NotNull
    private String userName;

    @NotNull
    private RoleEnum roleEnum;
}
