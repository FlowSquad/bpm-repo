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

    @NotNull
    private String bpmnRepositoryId;

    @NotEmpty
    private String bpmnDiagramName;

    @NotNull
    private String bpmnDiagramDescription;

    public BpmnDiagramTO(String bpmnRepositoryId, BpmnDiagramUploadTO bpmnDiagramUploadTO){
        this.bpmnDiagramId = bpmnDiagramUploadTO.getBpmnDiagramId();
        this.bpmnRepositoryId = bpmnRepositoryId;
        this.bpmnDiagramName = bpmnDiagramUploadTO.getBpmnDiagramName();
        this.bpmnDiagramDescription = bpmnDiagramUploadTO.getBpmnDiagramDescription();
    }

}
