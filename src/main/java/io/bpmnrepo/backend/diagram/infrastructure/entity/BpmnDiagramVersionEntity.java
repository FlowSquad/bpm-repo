package io.bpmnrepo.backend.diagram.infrastructure.entity;


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

    @Column(name = "bpmn_diagram_version_name", nullable = false)
    private String bpmnDiagramVersionName;


    //Counting versions automatically is not done like that (maybe also not wanted to be set automatically)
    @Version
    @Column(name = "bpmn_diagram_version_number", columnDefinition = "integer DEFAULT 0")
    private Long bpmnDiagramVersionNumber;

    //must not be nullable
    @Column(name = "bpmn_diagram_version_file")
    private byte bpmnDiagramVersionFile;

    @ManyToOne
    @JoinColumn(name = "bpmn_diagram_id", nullable = false)
    private BpmnDiagramEntity bpmnDiagramEntity;
}
