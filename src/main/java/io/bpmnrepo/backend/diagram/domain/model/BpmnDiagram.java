package io.bpmnrepo.backend.diagram.domain.model;


import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BpmnDiagram {

    private String bpmnDiagramId;
    private String bpmnDiagramName;
    private String bpmnDiagramDescription;
    private String bpmnRepositoryId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;



    public void updateDiagram(final BpmnDiagramTO bpmnDiagramTO, final BpmnDiagram bpmnDiagram) {
        if (bpmnDiagramTO.getBpmnDiagramName() != null || !bpmnDiagramTO.getBpmnDiagramName().isEmpty()) {
            this.setBpmnDiagramName(bpmnDiagramTO.getBpmnDiagramName());
        }
        if (bpmnDiagramTO.getBpmnDiagramDescription() != null || !bpmnDiagramTO.getBpmnDiagramDescription().isEmpty()) {
            this.setBpmnDiagramDescription(bpmnDiagramTO.getBpmnDiagramDescription());
        }
        this.setCreatedDate(bpmnDiagram.getCreatedDate());
    }

}
