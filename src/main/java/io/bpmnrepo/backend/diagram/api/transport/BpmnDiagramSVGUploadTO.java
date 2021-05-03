package io.bpmnrepo.backend.diagram.api.transport;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmnDiagramSVGUploadTO {
    @NotEmpty
    private String svgPreview;
}
