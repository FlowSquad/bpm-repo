package io.bpmnrepo.backend.diagram.domain.model;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BpmnDiagramVersion {
    private String bpmnDiagramVersionId;
    private String bpmnDiagramVersionName;
    private String bpmnDiagramId;
    private String bpmnRepositoryId;
    private Long bpmnDiagramVersionNumber;
    private byte[] bpmnDiagramVersionFile;


    //TODO: Version counter
    public BpmnDiagramVersion(final BpmnDiagramVersionTO bpmnDiagramVersionTO){
        this.bpmnDiagramVersionId = bpmnDiagramVersionTO.getBpmnDiagramVersionId();
        this.bpmnDiagramVersionName = bpmnDiagramVersionTO.getBpmnDiagramVersionName();
        this.bpmnDiagramVersionNumber = bpmnDiagramVersionTO.getBpmnDiagramVersionNumber() == null
                                        || bpmnDiagramVersionTO.getBpmnDiagramVersionName().isEmpty()
                                        || bpmnDiagramVersionTO.getBpmnDiagramVersionNumber() == 0L
                                            ? 1L
                                            : this.increaseVersionNumber(bpmnDiagramVersionTO.getBpmnDiagramVersionNumber());
        this.bpmnDiagramVersionFile = bpmnDiagramVersionTO.getBpmnDiagramVersionFile();
        this.bpmnDiagramId = bpmnDiagramVersionTO.getBpmnDiagramId();
        this.bpmnRepositoryId = bpmnDiagramVersionTO.getBpmnRepositoryId();
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
