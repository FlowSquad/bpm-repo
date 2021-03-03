package io.bpmnrepo.backend.diagram.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BpmnDiagramJpa extends JpaRepository<BpmnDiagramEntity, String> {

    // underscore in methodname to refer to child prop
    List<BpmnDiagramEntity> findBpmnDiagramEntitiesByBpmnDiagramRepository_BpmnRepositoryIdEquals(String bpmnDiagramRepositoryId);
    BpmnDiagramEntity findBpmnDiagramEntityByBpmnDiagramIdEquals(String bpmnDiagramId);

    @Transactional
    void deleteBpmnDiagramEntitiyByBpmnDiagramId(String bpmnDiagramId);

}
