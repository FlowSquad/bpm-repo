package io.bpmnrepo.backend.diagram.api.transport;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmnDiagramTO {

    @Nullable
    private String bpmnDiagramId;

    @NotNull
    private String bpmnRepositoryId;

    @NotNull
    private String bpmnDiagramName;

    @NotNull
    private String bpmnDiagramDescription;

}
