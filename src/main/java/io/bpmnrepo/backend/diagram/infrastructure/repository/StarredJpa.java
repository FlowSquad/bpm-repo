package io.bpmnrepo.backend.diagram.infrastructure.repository;

import io.bpmnrepo.backend.diagram.infrastructure.entity.StarredEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarredJpa extends JpaRepository<StarredEntity, String> {

    int deleteByStarredId_BpmnDiagramIdAndStarredId_UserId(String bpmnDiagramId, String userId);

    StarredEntity findByStarredId_BpmnDiagramIdAndStarredId_UserId(String bpmnDiagramId, String userId);

    List<StarredEntity> findAllByStarredId_UserId(String userId);

}
