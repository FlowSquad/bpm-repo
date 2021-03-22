package io.bpmnrepo.backend.repository.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class NewBpmnRepositoryTO {

    @NotBlank
    private String bpmnRepositoryName;

    @NotNull
    private String bpmnRepositoryDescription;

}
