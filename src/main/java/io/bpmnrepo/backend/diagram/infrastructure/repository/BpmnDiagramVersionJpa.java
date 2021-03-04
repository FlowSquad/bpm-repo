package io.bpmnrepo.backend.diagram.infrastructure.repository;

import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BpmnDiagramVersionJpa extends JpaRepository<BpmnDiagramVersionEntity, String> {

    List<BpmnDiagramVersionEntity> findAllByBpmnDiagramId(String bpmnDiagramId);

    BpmnDiagramVersionEntity findAllByBpmnDiagramVersionIdEquals(String bpmnDiagramVersionId);

    BpmnDiagramVersionEntity findFirstByBpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(String bpmnDiagramId);

    @Transactional
    int deleteAllByBpmnRepositoryId(String bpmnRepositoryId);
}
