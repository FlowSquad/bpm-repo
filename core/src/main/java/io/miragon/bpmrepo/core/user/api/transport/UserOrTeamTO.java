package io.miragon.bpmrepo.core.user.api.transport;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Containing Id, Name and EntityType of either a User or Team")
public class UserOrTeamTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    private String id;

    @NotBlank
    private boolean isTeam;

}
