package io.bpmnrepo.backend.diagram.domain.model;


import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BpmnDiagram {

    private String bpmnDiagramId;
    private String bpmnDiagramName;
    private String bpmnDiagramDescription;
    private String bpmnDiagramRepositoryId;
    private LocalDateTime createdDate;

    public BpmnDiagram(final BpmnDiagramTO bpmnDiagramTO){
        this.bpmnDiagramId = bpmnDiagramTO.getBpmnDiagramId();
        this.bpmnDiagramName = bpmnDiagramTO.getBpmnDiagramName();
        this.bpmnDiagramDescription = bpmnDiagramTO.getBpmnDiagramDescription();
        this.bpmnDiagramRepositoryId = bpmnDiagramTO.getBpmnDiagramRepositoryId();
        this.createdDate = LocalDateTime.now();
    }
}
