package io.bpmnrepo.backend.repository.api.transport;


import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BpmnRepositoryTO {

    @Nullable
    private String bpmnRepositoryId;

    @NotEmpty
    private String bpmnRepositoryName;

    @NotNull
    private String bpmnRepositoryDescription;


}
