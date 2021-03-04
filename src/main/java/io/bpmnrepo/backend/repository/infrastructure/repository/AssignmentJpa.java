package io.bpmnrepo.backend.repository.infrastructure.repository;

import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface AssignmentJpa extends JpaRepository<AssignmentEntity, String> {

    List<AssignmentEntity> findAssignmentEntitiesByAssignmentId_UserIdEquals(String userId);

    AssignmentEntity findByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(String bpmnRepositoryId, String userId);


    @Transactional
    int deleteAllByAssignmentId_BpmnRepositoryId(String bpmnRepositoryId);
}
