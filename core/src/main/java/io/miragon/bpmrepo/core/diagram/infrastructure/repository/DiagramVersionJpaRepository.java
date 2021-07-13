package io.miragon.bpmrepo.core.diagram.infrastructure.repository;

import io.miragon.bpmrepo.core.diagram.domain.enums.SaveTypeEnum;
import io.miragon.bpmrepo.core.diagram.infrastructure.entity.DiagramVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiagramVersionJpaRepository extends JpaRepository<DiagramVersionEntity, String> {

    Optional<DiagramVersionEntity> findById(String versionId);

    List<DiagramVersionEntity> findAllByDiagramId(String bpmnDiagramId);

    Optional<DiagramVersionEntity> findFirstByDiagramIdOrderByMilestoneDesc(String bpmnDiagramId);

    DiagramVersionEntity findFirstByDiagramIdAndRepositoryIdOrderByMilestoneDesc(String bpmnDiagramId, String bpmnRepositoryId);

    int deleteAllByRepositoryId(String bpmnRepositoryId);

    int deleteAllByDiagramId(String bpmnDiagramId);

    int deleteAllByRepositoryIdAndDiagramIdAndSaveType(String bpmnRepositoryId, String bpmnDiagramId, SaveTypeEnum saveTypeEnum);

    DiagramVersionEntity save(DiagramVersionEntity entity);
}
