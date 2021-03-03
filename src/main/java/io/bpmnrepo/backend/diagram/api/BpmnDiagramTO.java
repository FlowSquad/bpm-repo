package io.bpmnrepo.backend.diagram.api;

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
    private String bpmnDiagramRepositoryId;

    @NotNull
    private String bpmnDiagramName;

    @NotNull
    private String bpmnDiagramDescription;

}
