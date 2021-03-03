package io.bpmnrepo.backend.version.domain;

import io.bpmnrepo.backend.version.api.BpmnDiagramVersionTO;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BpmnDiagramVersion {
    private String bpmnDiagramVersionId;
    private String bpmnDiagramVersionName;
    private String bpmnDiagramId;
    private Long bpmnDiagramVersionNumber;
    private byte bpmnDiagramVersionFile;


    //TODO: Version counter
    public BpmnDiagramVersion(final BpmnDiagramVersionTO bpmnDiagramVersionTO){
        this.bpmnDiagramVersionId = bpmnDiagramVersionTO.getBpmnDiagramVersionId();
        this.bpmnDiagramVersionName = bpmnDiagramVersionTO.getBpmnDiagramVersionName();
        this.bpmnDiagramId = bpmnDiagramVersionTO.getBpmnDiagramId();
        this.bpmnDiagramVersionNumber = bpmnDiagramVersionTO.getBpmnDiagramVersionNumber() == null
                                            ? 1L
                                            : this.increaseVersionNumber(bpmnDiagramVersionTO.getBpmnDiagramVersionNumber());
        this.bpmnDiagramVersionFile = bpmnDiagramVersionTO.getBpmnDiagramVersionFile();
    }

    public Long increaseVersionNumber(Long currentVersion){
        return currentVersion + 1;
    }

    public void updateBpmnDiagramVersion(final BpmnDiagramVersionTO bpmnDiagramVersionTO){
        this.bpmnDiagramVersionId = null;
        this.bpmnDiagramVersionName = bpmnDiagramVersionTO.getBpmnDiagramVersionName();
        this.bpmnDiagramId = bpmnDiagramVersionTO.getBpmnDiagramId();
        this.bpmnDiagramVersionNumber = bpmnDiagramVersionTO.getBpmnDiagramVersionNumber() + 1;
        this.bpmnDiagramVersionFile = bpmnDiagramVersionTO.getBpmnDiagramVersionFile();

    }
}
