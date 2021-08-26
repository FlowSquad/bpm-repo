package io.miragon.bpmrepo.core.artifact.api.transport;


import io.miragon.bpmrepo.core.shared.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Share-relation between an artifact an a team or a repository")
public class ShareWithTeamTO {

    @NotBlank
    private String artifactId;

    @NotBlank
    private String teamId;

    @NotBlank
    private RoleEnum role;
}
