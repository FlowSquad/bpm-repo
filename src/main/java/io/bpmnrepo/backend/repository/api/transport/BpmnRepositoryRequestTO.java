package io.bpmnrepo.backend.repository.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BpmnRepositoryRequestTO {
    @NotEmpty
    private String bpmnRepositoryId;

    @NotEmpty
    private String bpmnRepositoryName;

    @NotNull
    private String bpmnRepositoryDescription;

    @NotNull
    private Integer existingDiagrams;

    @NotNull
    private Integer assignedUsers;
}
