package io.bpmnrepo.backend.shared.mapper;

import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentId;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.repository.domain.model.Assignment;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagram;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagramVersion;
import io.bpmnrepo.backend.repository.domain.model.BpmnRepository;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.user.api.transport.UserTO;
import io.bpmnrepo.backend.user.infrastructure.entity.UserEntity;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramVersionEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Mapper {

    //___________________________TO -> Embeddable______________________________-

    public AssignmentId toEmbeddable(String userId, String bpmnRepositoryId) {
        if (userId == null || bpmnRepositoryId == null) {
            return null;
        }
        return AssignmentId.builder()
                .userId(userId)
                .bpmnRepositoryId(bpmnRepositoryId)
                .build();
    }
}
