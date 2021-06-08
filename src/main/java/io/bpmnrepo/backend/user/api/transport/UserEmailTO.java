package io.bpmnrepo.backend.user.api.transport;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEmailTO {

    @Email
    @NotEmpty
    private String email;
}
