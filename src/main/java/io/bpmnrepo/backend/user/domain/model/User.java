package io.bpmnrepo.backend.user.domain.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotNull
    private String userName;

}
