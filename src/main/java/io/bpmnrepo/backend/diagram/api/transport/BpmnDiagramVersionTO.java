package io.bpmnrepo.backend.diagram.api.transport;

import com.sun.istack.Nullable;
import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
import lombok.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmnDiagramVersionTO {

    @Nullable
    private String bpmnDiagramVersionId;

    @Nullable
    private String bpmnDiagramVersionComment;

    @Nullable
    private Integer bpmnDiagramVersionRelease;

    @Nullable
    private Integer bpmnDiagramVersionMilestone;

    @NotEmpty
    private String bpmnAsXML;

    @NotEmpty
    private SaveTypeEnum saveType;

    @NotEmpty
    private String bpmnDiagramId;

    @NotEmpty
    private String bpmnRepositoryId;


    public BpmnDiagramVersionTO(String bpmnRepositoryId, String bpmnDiagramId, BpmnDiagramVersionUploadTO bpmnDiagramVersionUploadTO){
        this.bpmnRepositoryId = bpmnRepositoryId;
        this.bpmnDiagramId = bpmnDiagramId;
        this.bpmnDiagramVersionComment = bpmnDiagramVersionUploadTO.getBpmnDiagramVersionComment();
        this.bpmnAsXML = bpmnDiagramVersionUploadTO.getBpmnAsXML();
        this.saveType = bpmnDiagramVersionUploadTO.getSaveType();
    }
}
