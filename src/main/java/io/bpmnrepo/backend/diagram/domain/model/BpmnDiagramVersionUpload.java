package io.bpmnrepo.backend.diagram.domain.model;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionUploadTO;
import lombok.*;

import java.nio.charset.StandardCharsets;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BpmnDiagramVersionUpload {
    private byte[] bpmnDiagramVersionFile;
    private String bpmnRepositoryId;
    private String bpmnDiagramId;
    private String bpmnDiagramVersionNumber;

    public BpmnDiagramVersionUpload(final BpmnDiagramVersionUploadTO bpmnDiagramVersionUploadTO){
        this.bpmnRepositoryId = bpmnDiagramVersionUploadTO.getBpmnRepositoryId();
        this.bpmnDiagramId = bpmnDiagramVersionUploadTO.getBpmnDiagramId();
        this.bpmnDiagramVersionFile = bpmnDiagramVersionUploadTO.getBpmnAsXML().getBytes();
    }

}
