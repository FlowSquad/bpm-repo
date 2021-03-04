package io.bpmnrepo.backend.diagram.infrastructure.repository;

import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BpmnDiagramJpa extends JpaRepository<BpmnDiagramEntity, String> {

    // underscore in methodname to refer to child prop
    List<BpmnDiagramEntity> findBpmnDiagramEntitiesByBpmnRepositoryId(String bpmnDiagramRepositoryId);
    BpmnDiagramEntity findBpmnDiagramEntityByBpmnDiagramIdEquals(String bpmnDiagramId);

    @Transactional
    int deleteBpmnDiagramEntitiyByBpmnDiagramId(String bpmnDiagramId);

    @Transactional
    int deleteAllByBpmnRepositoryId(String bpmnRepositoryId);
}
