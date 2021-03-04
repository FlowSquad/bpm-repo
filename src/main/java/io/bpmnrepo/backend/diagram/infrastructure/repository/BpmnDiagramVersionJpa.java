package io.bpmnrepo.backend.diagram.infrastructure.repository;

import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BpmnDiagramVersionJpa extends JpaRepository<BpmnDiagramVersionEntity, String> {

    List<BpmnDiagramVersionEntity> findAllByBpmnDiagramEntity_BpmnDiagramId(String bpmnDiagramId);

    BpmnDiagramVersionEntity findAllByBpmnDiagramVersionIdEquals(String bpmnDiagramVersionId);

    BpmnDiagramVersionEntity findFirstByBpmnDiagramEntity_BpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(String bpmnDiagramId);

    @Transactional
    int deleteBpmnDiagramVersionEntitiesByBpmnDiagramEntity_BpmnDiagramId(String bpmnDiagramId);

}
