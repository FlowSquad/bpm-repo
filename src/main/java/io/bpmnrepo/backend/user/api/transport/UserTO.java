package io.bpmnrepo.backend.user.api.transport;

import com.sun.istack.Nullable;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTO {

    @NotEmpty
    @Size(min = 3, max = 50)
    private String userName;

    @Nullable
    private String apiKey;

    @Email
    @NotEmpty
    private String email;
}
