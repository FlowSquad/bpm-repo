package io.bpmnrepo.backend.diagram.api.transport;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

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
    private Long bpmnDiagramVersionNumber;

    //must not be nullable in the end
    @NotNull
    private String bpmnAsXML;

    //@NotNull
    @Nullable
    private String bpmnDiagramId;

    //@NotNull
    @Nullable
    private String bpmnRepositoryId;

}
