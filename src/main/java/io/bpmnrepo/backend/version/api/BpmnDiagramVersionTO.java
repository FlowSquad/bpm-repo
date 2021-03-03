package io.bpmnrepo.backend.version.api;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.*;

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

    @NotNull
    private String bpmnDiagramId;

    @Nullable
    private Long bpmnDiagramVersionNumber;

    //must not be nullable in the end
    @Nullable
    private byte bpmnDiagramVersionFile;

    //Version number automatically generated

}
