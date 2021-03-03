package io.bpmnrepo.backend.diagram.infrastructure;


import io.bpmnrepo.backend.repository.infrastructure.BpmnRepositoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bpmnDiagram")
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"bpmn_diagram_id", "bpmn_repository_id"}))
public class BpmnDiagramEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "bpmn_diagram_id", unique = true, nullable = false, updatable = false, length = 36)
    private String bpmnDiagramId;

    @ManyToOne
    @JoinColumn(name = "bpmn_diagram_repository")
    private BpmnRepositoryEntity bpmnDiagramRepository;

    @Column(name = "bpmn_diagram_name")
    private String bpmnDiagramName;

    @Column(name = "bpmd_diagram_description")
    private String bpmnDiagramDescription;

    //This one causes JPA to crash, don't know why yet


/*    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "bpmn_diagram_version_id", nullable = true, updatable = true)
    private BpmnDiagramVersionEntity bpmnDiagramVersionId;*/
}
