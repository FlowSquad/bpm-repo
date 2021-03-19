package io.bpmnrepo.backend.diagram.api.transport;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmnDiagramUploadTO {

    @Nullable
    private String bpmnDiagramId;

    @NotEmpty
    private String bpmnDiagramName;

    @NotNull
    private String bpmnDiagramDescription;
}
