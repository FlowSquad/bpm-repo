package io.bpmnrepo.backend.diagram.infrastructure.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bpmnDiagramVersion")
public class BpmnDiagramVersionEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "bpmn_diagram_version_id", unique = true, nullable = false, updatable = false, length = 36)
    private String bpmnDiagramVersionId;

    @Column(name = "bpmn_diagram_version_name", nullable = false)
    private String bpmnDiagramVersionName;

    @Column(name = "bpmn_diagram_version_number")
    private Long bpmnDiagramVersionNumber;

    //must not be nullable
    @Column(name = "bpmn_diagram_version_file")
    private byte[] bpmnDiagramVersionFile;

    @Column(name = "bpmn_diagram_id", nullable = false)
    private String bpmnDiagramId;

    @Column(name = "bpmn_repository_id", nullable = false)
    private String bpmnRepositoryId;
}
