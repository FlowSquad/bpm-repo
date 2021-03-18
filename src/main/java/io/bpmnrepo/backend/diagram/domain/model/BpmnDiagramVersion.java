package io.bpmnrepo.backend.diagram.domain.model;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionUploadTO;
import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
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
    private Integer bpmnDiagramVersionRelease;
    private Integer bpmnDiagramVersionMilestone;
    private byte[] bpmnDiagramVersionFile;
    private SaveTypeEnum saveType;


    public BpmnDiagramVersion(final BpmnDiagramVersionTO bpmnDiagramVersionTO) {
        this.bpmnDiagramVersionId = bpmnDiagramVersionTO.getBpmnDiagramVersionId();
        this.bpmnDiagramVersionComment = bpmnDiagramVersionTO.getBpmnDiagramVersionComment();
        this.bpmnDiagramVersionRelease = generateReleaseNumber(bpmnDiagramVersionTO);
        this.bpmnDiagramVersionMilestone = generateMilestoneNumber(bpmnDiagramVersionTO);
        this.bpmnDiagramVersionFile = bpmnDiagramVersionTO.getBpmnAsXML().getBytes();
        this.bpmnDiagramId = bpmnDiagramVersionTO.getBpmnDiagramId();
        this.bpmnRepositoryId = bpmnDiagramVersionTO.getBpmnRepositoryId();
        this.saveType = bpmnDiagramVersionTO.getSaveType();
    }


    public Integer generateReleaseNumber(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        if(bpmnDiagramVersionTO.getBpmnDiagramVersionRelease() != null) {
            switch (bpmnDiagramVersionTO.getSaveType()){
                case AUTOSAVE:
                    return bpmnDiagramVersionTO.getBpmnDiagramVersionRelease();
                case MILESTONE:
                    return  bpmnDiagramVersionTO.getBpmnDiagramVersionRelease();
                case RELEASE:
                    return bpmnDiagramVersionTO.getBpmnDiagramVersionRelease() +1;
                default:
                    return bpmnDiagramVersionTO.getBpmnDiagramVersionRelease();
            }
        }
        else{
            return 1;
        }
    }

    public Integer generateMilestoneNumber(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        if(bpmnDiagramVersionTO.getBpmnDiagramVersionMilestone() != null) {
            switch (bpmnDiagramVersionTO.getSaveType()){
                case AUTOSAVE:
                    return bpmnDiagramVersionTO.getBpmnDiagramVersionMilestone();
                case MILESTONE:
                    return bpmnDiagramVersionTO.getBpmnDiagramVersionMilestone() + 1;
                case RELEASE:
                    return 0;
                default:
                    return 0;
            }
        }
        else{
            return 0;
        }
    }
}
