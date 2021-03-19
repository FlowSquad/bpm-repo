package io.bpmnrepo.backend.diagram.infrastructure.repository;

import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BpmnDiagramJpa extends JpaRepository<BpmnDiagramEntity, String> {

    List<BpmnDiagramEntity> findBpmnDiagramEntitiesByBpmnRepositoryId(String bpmnDiagramRepositoryId);
    BpmnDiagramEntity findBpmnDiagramEntityByBpmnDiagramIdEquals(String bpmnDiagramId);
    BpmnDiagramEntity findBpmnDiagramEntityByBpmnRepositoryIdAndBpmnDiagramName(String bpmnRepositoryId, String bpmnDiagramName);
    int deleteBpmnDiagramEntitiyByBpmnDiagramId(String bpmnDiagramId);
    int deleteAllByBpmnRepositoryId(String bpmnRepositoryId);
}
