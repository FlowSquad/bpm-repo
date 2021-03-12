package io.bpmnrepo.backend.diagram.domain.model;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BpmnDiagramVersion {
    private String bpmnDiagramVersionId;
    private String bpmnDiagramVersionComment;
    private String bpmnDiagramId;
    private String bpmnRepositoryId;
    private Long bpmnDiagramVersionNumber;
    private byte[] bpmnDiagramVersionFile;


    //TODO: Version counter
    public BpmnDiagramVersion(final BpmnDiagramVersionTO bpmnDiagramVersionTO) {
        this.bpmnDiagramVersionId = bpmnDiagramVersionTO.getBpmnDiagramVersionId();
        this.bpmnDiagramVersionComment = bpmnDiagramVersionTO.getBpmnDiagramVersionComment();
        this.bpmnDiagramVersionNumber = bpmnDiagramVersionTO.getBpmnDiagramVersionNumber() == null
                                        || bpmnDiagramVersionTO.getBpmnDiagramVersionNumber() == 0L
                                            ? 1L
                                            : this.increaseVersionNumber(bpmnDiagramVersionTO.getBpmnDiagramVersionNumber());
        this.bpmnDiagramVersionFile = bpmnDiagramVersionTO.getBpmnAsXML().getBytes();
        this.bpmnDiagramId = bpmnDiagramVersionTO.getBpmnDiagramId();
        this.bpmnRepositoryId = bpmnDiagramVersionTO.getBpmnRepositoryId();
    }

    public BpmnDiagramVersion(final BpmnDiagramVersionUpload bpmnDiagramVersionUpload){
        this.bpmnDiagramVersionId = null;
        this.bpmnDiagramVersionNumber = 1L;
        this.bpmnDiagramVersionFile = bpmnDiagramVersionUpload.getBpmnDiagramVersionFile();
        this.bpmnDiagramId = bpmnDiagramVersionUpload.getBpmnDiagramId();
        this.bpmnRepositoryId = bpmnDiagramVersionUpload.getBpmnRepositoryId();
    }

    public Long increaseVersionNumber(Long currentVersion){
        return currentVersion + 1;
    }

/*    public void updateBpmnDiagramVersion(final BpmnDiagramVersionTO bpmnDiagramVersionTO){
        this.bpmnDiagramVersionId = null;
        this.bpmnDiagramVersionName = bpmnDiagramVersionTO.getBpmnDiagramVersionName();
        this.bpmnDiagramId = bpmnDiagramVersionTO.getBpmnDiagramId();
        this.bpmnDiagramVersionNumber = bpmnDiagramVersionTO.getBpmnDiagramVersionNumber() + 1;
        this.bpmnDiagramVersionFile = bpmnDiagramVersionTO.getBpmnDiagramVersionFile();

    }*/
}
