package io.bpmnrepo.backend.diagram.infrastructure.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;

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

    @Column(name = "bpmn_diagram_version_number")
    private Long bpmnDiagramVersionNumber;

    @Lob
    @Column(name = "bpmn_diagram_version_file")
    private byte[] bpmnDiagramVersionFile;

    @Column(name = "bpmn_diagram_id", nullable = false)
    private String bpmnDiagramId;

    @Column(name = "bpmn_repository_id", nullable = false)
    private String bpmnRepositoryId;
}
