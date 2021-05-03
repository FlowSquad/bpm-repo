package io.bpmnrepo.backend.diagram.domain.business;

import io.bpmnrepo.backend.diagram.domain.mapper.StarredMapper;
import io.bpmnrepo.backend.diagram.domain.model.Starred;
import io.bpmnrepo.backend.diagram.infrastructure.entity.StarredEntity;
import io.bpmnrepo.backend.diagram.infrastructure.entity.StarredId;
import io.bpmnrepo.backend.diagram.infrastructure.repository.StarredJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarredService {

    private final StarredJpa starredJpa;
    private final StarredMapper mapper;


    public void createStarred(String bpmnDiagramId, String userId) {
        //Starred starred = new Starred(bpmnDiagramId, userId);
        StarredId starredId = this.mapper.toEmbeddable(bpmnDiagramId, userId);
        StarredEntity starredEntity = this.mapper.toEntity(starredId);
        this.starredJpa.save(starredEntity);
    }

    public void deleteStarred(String bpmnDiagramId, String userId) {
        this.starredJpa.deleteByStarredId_BpmnDiagramIdAndStarredId_UserId(bpmnDiagramId, userId);
    }

    public List<StarredEntity> getStarred(String userId) {
         return this.starredJpa.findAllByStarredId_UserId(userId);

    }
}
