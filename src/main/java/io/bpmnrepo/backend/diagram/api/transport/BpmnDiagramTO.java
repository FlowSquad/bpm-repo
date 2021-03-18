package io.bpmnrepo.backend.diagram.api.transport;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmnDiagramTO {

    @Nullable
    private String bpmnDiagramId;

    @Nullable
    private String bpmnRepositoryId;

    @NotEmpty
    private String bpmnDiagramName;

    @NotNull
    private String bpmnDiagramDescription;

}
