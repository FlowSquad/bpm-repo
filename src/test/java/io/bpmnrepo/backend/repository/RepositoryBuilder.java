package io.bpmnrepo.backend.repository;

import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.api.transport.NewBpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.model.BpmnRepository;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentId;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import io.bpmnrepo.backend.shared.enums.RoleEnum;

import java.time.LocalDateTime;

public class RepositoryBuilder {

    public static BpmnRepository buildRepo(final String repoId, final String repoName, final String repoDesc, final LocalDateTime createdDate, final LocalDateTime updatedDate){
        return BpmnRepository.builder()
                .bpmnRepositoryId(repoId)
                .bpmnRepositoryName(repoName)
                .bpmnRepositoryDescription(repoDesc)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }

    public static NewBpmnRepositoryTO buildNewRepoTO(final String repoName, final String repoDesc){
        return NewBpmnRepositoryTO.builder()
                .bpmnRepositoryName(repoName)
                .bpmnRepositoryDescription(repoDesc)
                .build();
    }


    public static BpmnRepositoryTO buildRepoTO(final String repoName, final String repoDesc){
        return BpmnRepositoryTO.builder()
                .bpmnRepositoryName(repoName)
                .bpmnRepositoryDescription(repoDesc)
                .build();
    }

    public static BpmnRepositoryEntity buildRepoEntity(final String repoId, final String repoName, final String repoDesc, final LocalDateTime createdDate, final LocalDateTime updatedDate) {
        return BpmnRepositoryEntity.builder()
                .bpmnRepositoryId(repoId)
                .bpmnRepositoryName(repoName)
                .bpmnRepositoryDescription(repoDesc)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }


    public static AssignmentEntity buildAssignment(final String userId, final String bpmnRepositoryId, final RoleEnum roleEnum){
        AssignmentId assignmentId = AssignmentId.builder()
                                                .userId(userId)
                                                .bpmnRepositoryId(bpmnRepositoryId)
                                                .build();

        return AssignmentEntity.builder()
                                .assignmentId(assignmentId)
                                .roleEnum(roleEnum)
                                .build();
    }


}
