package io.bpmnrepo.backend.diagram.api.transport;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmnDiagramVersionUploadTO {

    @NotNull
    private String bpmnAsXML;

    @Nullable
    private String bpmnRepositoryId;

    @Nullable
    private String bpmnDiagramId;
}
