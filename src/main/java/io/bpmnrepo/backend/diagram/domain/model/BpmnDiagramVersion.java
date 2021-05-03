package io.bpmnrepo.backend.diagram.domain.model;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionUploadTO;
import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
import lombok.*;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
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


    public void updateVersion(final BpmnDiagramVersionTO bpmnDiagramVersionTO){
        if (bpmnDiagramVersionTO.getBpmnDiagramVersionComment() == null || bpmnDiagramVersionTO.getBpmnDiagramVersionComment().isEmpty()) {
            this.setBpmnDiagramVersionComment(this.getBpmnDiagramVersionComment());
        } else {
            this.setBpmnDiagramVersionComment(bpmnDiagramVersionTO.getBpmnDiagramVersionComment());
        }
        this.setBpmnDiagramVersionRelease(generateReleaseNumber(bpmnDiagramVersionTO));
        this.setBpmnDiagramVersionMilestone(generateMilestoneNumber(bpmnDiagramVersionTO));
        this.setBpmnDiagramVersionFile(bpmnDiagramVersionTO.getBpmnAsXML().getBytes());
    }


    public Integer generateReleaseNumber(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        if(this.getBpmnDiagramVersionRelease() != null) {
            if (bpmnDiagramVersionTO.getSaveType().equals(SaveTypeEnum.RELEASE)) {
                return this.getBpmnDiagramVersionRelease() + 1;
            } else {
                return this.getBpmnDiagramVersionRelease();
            }
        }
        else{
            return 1;
        }
    }

    public Integer generateMilestoneNumber(BpmnDiagramVersionTO bpmnDiagramVersionTO){
            switch (bpmnDiagramVersionTO.getSaveType()){
                case AUTOSAVE:
                    return this.getBpmnDiagramVersionMilestone();
                case MILESTONE:
                    return this.getBpmnDiagramVersionMilestone() + 1;
                default:
                    return 0;
            }
    }
}
