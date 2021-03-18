package io.bpmnrepo.backend.diagram.api.transport;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmnDiagramVersionUploadTO {

    @Nullable
    private String bpmnDiagramVersionComment;

    @NotNull
    private String bpmnAsXML;

    private SaveTypeEnum saveType;

}
