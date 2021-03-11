package io.bpmnrepo.backend.diagram.api.transport;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmnDiagramVersionTO {

    @Nullable
    private String bpmnDiagramVersionId;

    @Nullable
    private String bpmnDiagramVersionName;

    @Nullable
    private Long bpmnDiagramVersionNumber;

    //must not be nullable in the end
    @Nullable
    private byte[] bpmnDiagramVersionFile;

    @NotNull
    private String bpmnDiagramId;

    @NotNull
    private String bpmnRepositoryId;
}
