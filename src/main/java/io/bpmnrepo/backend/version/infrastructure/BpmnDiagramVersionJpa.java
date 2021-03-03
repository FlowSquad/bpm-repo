package io.bpmnrepo.backend.version.infrastructure;

import io.bpmnrepo.backend.version.infrastructure.BpmnDiagramVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BpmnDiagramVersionJpa extends JpaRepository<BpmnDiagramVersionEntity, String> {

    List<BpmnDiagramVersionEntity> findAllByBpmnDiagramEntity_BpmnDiagramId(String bpmnDiagramId);
    BpmnDiagramVersionEntity findAllByBpmnDiagramVersionIdEquals(String bpmnDiagramVersionId);
    BpmnDiagramVersionEntity findFirstByBpmnDiagramEntity_BpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(String bpmnDiagramId);
    @Transactional
    void deleteBpmnDiagramVersionEntitiesByBpmnDiagramEntity_BpmnDiagramId(String bpmnDiagramId);
}
