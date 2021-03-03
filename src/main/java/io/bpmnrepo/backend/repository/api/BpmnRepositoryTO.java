package io.bpmnrepo.backend.repository.api;


import lombok.*;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnRepositoryTO {

    @Nullable
    private String bpmnRepositoryId;

    @NotNull
    private String bpmnRepositoryName;

    @NotNull
    private String bpmnRepositoryDescription;

/*    @Nullable
    private RoleEnum roleEnum;*/

}
