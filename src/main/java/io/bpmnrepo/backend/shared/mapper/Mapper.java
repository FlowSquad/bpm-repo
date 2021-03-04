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


    //_______________________TO --> Entity_____________________________
    public BpmnDiagramEntity toEntity(final BpmnDiagramTO to, final BpmnRepositoryEntity bpmnRepositoryEntity) {
        if (to == null) {
            return null;
        }
        return BpmnDiagramEntity.builder()
                .bpmnDiagramId(to.getBpmnDiagramId())
                .bpmnDiagramRepository(bpmnRepositoryEntity) //hier muss eine komplette BpmnRepositoryEntity mitgegeben werden (weil @OneToMany in der Entity klasse den Entitydatentyp fordert) -> RepoID gegeben ==> erst Repo durch ID finden
                .bpmnDiagramName(to.getBpmnDiagramName())
                .bpmnDiagramDescription(to.getBpmnDiagramDescription())
                .build();
    }


    public BpmnRepositoryEntity toEntity(final BpmnRepositoryTO to) {
        if (to == null) {
            return null;
        }
        return BpmnRepositoryEntity.builder()
                .bpmnRepositoryId(to.getBpmnRepositoryId())
                .bpmnRepositoryName(to.getBpmnRepositoryName())
                .bpmnRepositoryDescription(to.getBpmnRepositoryDescription())
                .createdDate(LocalDateTime.now())
                .build();

    }


    public BpmnDiagramVersionEntity toEntity(final BpmnDiagramVersionTO to, final BpmnDiagramEntity entity) {
        if (to == null) {
            return null;
        }
        return BpmnDiagramVersionEntity.builder()
                .bpmnDiagramVersionName(to.getBpmnDiagramVersionName())
                .bpmnDiagramVersionFile(to.getBpmnDiagramVersionFile())
                .bpmnDiagramEntity(entity)
                .build();

    }


    public UserEntity toEntity(final UserTO to) {
        if (to == null) {
            return null;
        }
        return UserEntity.builder()
                .username(to.getUserName())
                .build();

    }


    //___________________________Entity -> TO _______________________________

    //raus
    public BpmnRepositoryTO toTO(final BpmnRepositoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return BpmnRepositoryTO.builder()
                .bpmnRepositoryId(entity.getBpmnRepositoryId())
                .bpmnRepositoryName(entity.getBpmnRepositoryName())
                .bpmnRepositoryDescription(entity.getBpmnRepositoryDescription())
                .build();
    }

    public BpmnDiagramTO toTO(final BpmnDiagramEntity entity) {
        if (entity == null) {
            return null;
        }
        return BpmnDiagramTO.builder()
                .bpmnDiagramId(entity.getBpmnDiagramId())
                .bpmnDiagramRepositoryId(entity.getBpmnDiagramRepository().getBpmnRepositoryId())
                .bpmnDiagramName(entity.getBpmnDiagramName())
                .bpmnDiagramDescription(entity.getBpmnDiagramDescription())
                .build();
    }

    public BpmnDiagramVersionTO toTO(final BpmnDiagramVersionEntity entity) {
        if (entity == null) {
            return null;
        }
        return BpmnDiagramVersionTO.builder()
                .bpmnDiagramVersionId(entity.getBpmnDiagramVersionId())
                .bpmnDiagramVersionName(entity.getBpmnDiagramVersionName())
                .bpmnDiagramVersionNumber(entity.getBpmnDiagramVersionNumber())
                .bpmnDiagramVersionFile(entity.getBpmnDiagramVersionFile())
                .build();
    }


    //___________________________TO -> Embeddable______________________________-

    public AssignmentId toEmbeddable(String userId, String bpmnRepositoryId){
        if(userId == null || bpmnRepositoryId == null){
            return null;
        }
        return AssignmentId.builder()
                .userId(userId)
                .bpmnRepositoryId(bpmnRepositoryId)
                .build();
    }

    //___________________________ TO -> Model ___________________________________
    public BpmnRepository toModel(BpmnRepositoryTO to){
        if(to == null){
            return null;
        }
        return BpmnRepository.builder()
                .bpmnRepositoryId(to.getBpmnRepositoryId())
                .bpmnRepositoryName(to.getBpmnRepositoryName())
                .bpmnRepositoryDescription(to.getBpmnRepositoryDescription())
                .build();
    }

    public BpmnDiagram toModel(BpmnDiagramTO to){
        if(to == null){
            return null;
        }
        return BpmnDiagram.builder()
                .bpmnDiagramId(to.getBpmnDiagramId())
                .bpmnDiagramName(to.getBpmnDiagramName())
                .bpmnDiagramDescription(to.getBpmnDiagramDescription())
                .bpmnDiagramRepositoryId(to.getBpmnDiagramRepositoryId())
                .build();
    }

    public BpmnDiagramVersion toModel(BpmnDiagramVersionTO to){
        if(to == null){
            return null;
        }
        return BpmnDiagramVersion.builder()
                .bpmnDiagramVersionId(to.getBpmnDiagramVersionId())
                .bpmnDiagramVersionName(to.getBpmnDiagramVersionName())
                .bpmnDiagramId(to.getBpmnDiagramId())
                .bpmnDiagramVersionNumber(to.getBpmnDiagramVersionNumber())
                .bpmnDiagramVersionFile(to.getBpmnDiagramVersionFile())
                .build();
    }

    //_____________________________ Model -> Entity _______________________________

    //raus
    public BpmnRepositoryEntity toEntity(BpmnRepository model){
        if(model == null){
            return null;
        }
        return BpmnRepositoryEntity.builder()
                .bpmnRepositoryId(model.getBpmnRepositoryId())
                .bpmnRepositoryName(model.getBpmnRepositoryName())
                .bpmnRepositoryDescription(model.getBpmnRepositoryDescription())
                .createdDate(model.getCreatedDate())
                .updatedDate(model.getUpdatedDate())
                .build();
    }

    public BpmnDiagramEntity toEntity(BpmnDiagram model, BpmnRepositoryEntity bpmnRepositoryEntity){
        if(model == null){
            return null;
        }
        return BpmnDiagramEntity.builder()
                .bpmnDiagramId(model.getBpmnDiagramId())
                .bpmnDiagramName(model.getBpmnDiagramName())
                .bpmnDiagramDescription(model.getBpmnDiagramDescription())
                .bpmnDiagramRepository(bpmnRepositoryEntity)
                .build();
    }

    public BpmnDiagramVersionEntity toEntity(BpmnDiagramVersion model, BpmnDiagramEntity bpmnDiagramEntity){
        if(model == null){
            return null;
        }
        return BpmnDiagramVersionEntity.builder()
                .bpmnDiagramVersionId(model.getBpmnDiagramId())
                .bpmnDiagramVersionName(model.getBpmnDiagramVersionName())
                .bpmnDiagramEntity(bpmnDiagramEntity)
                .bpmnDiagramVersionNumber(model.getBpmnDiagramVersionNumber())
                .bpmnDiagramVersionFile(model.getBpmnDiagramVersionFile())
                .build();
    }

    public AssignmentEntity toEntity(Assignment model, AssignmentId assignmentId){
        if(model == null){
            return null;
        }
        return AssignmentEntity.builder()
                .assignmentId(assignmentId)
                .roleEnum(model.getRoleEnum())
                .build();
    }
}