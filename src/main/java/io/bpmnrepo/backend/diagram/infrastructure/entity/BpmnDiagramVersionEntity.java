package io.bpmnrepo.backend.diagram.infrastructure.entity;


import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
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

    @Column(name = "bpmn_diagram_version_comment")
    private String bpmnDiagramVersionComment;

    @Column(name = "bpmn_diagram_version_release", nullable = false)
    private Integer bpmnDiagramVersionRelease;

    @Column(name = "bpmn_diagram_version_milestone", nullable = false)
    private Integer bpmnDiagramVersionMilestone;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "save_type", nullable = false)
    private SaveTypeEnum saveType;

    @Lob
    @Column(name = "bpmn_diagram_version_file")
    private byte[] bpmnDiagramVersionFile;

    @Column(name = "bpmn_diagram_id", nullable = false)
    private String bpmnDiagramId;

    @Column(name = "bpmn_repository_id", nullable = false)
    private String bpmnRepositoryId;
}
