package io.bpmnrepo.backend.diagram;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagram;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;

import java.time.LocalDateTime;

public class DiagramBuilder {

    public static BpmnDiagram buildDiagram(final String diagramId, final String repoId, final String diagramName, final String diagramDesc, final LocalDateTime createdDate, final LocalDateTime updatedDate){
        return BpmnDiagram.builder()
                .bpmnDiagramId(diagramId)
                .bpmnRepositoryId(repoId)
                .bpmnDiagramName(diagramName)
                .bpmnDiagramDescription(diagramDesc)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }

    public static BpmnDiagramEntity buildDiagramEntity(final String diagramId, final String repoId, final String diagramName, final String diagramDesc, final LocalDateTime createdDate, final LocalDateTime updatedDate) {
        return BpmnDiagramEntity.builder()
                .bpmnDiagramId(diagramId)
                .bpmnRepositoryId(repoId)
                .bpmnDiagramName(diagramName)
                .bpmnDiagramDescription(diagramDesc)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }

    public static BpmnDiagramTO buildDiagramTO(final String diagramId, final String repoId, final String diagramName, final String diagramDesc){
        return BpmnDiagramTO.builder()
                .bpmnDiagramId(diagramId)
                .bpmnRepositoryId(repoId)
                .bpmnDiagramName(diagramName)
                .bpmnDiagramDescription(diagramDesc)
                .build();
    }

}
