package io.bpmnrepo.backend.user.api;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTO {
    @NotNull
    private String userName;

}
