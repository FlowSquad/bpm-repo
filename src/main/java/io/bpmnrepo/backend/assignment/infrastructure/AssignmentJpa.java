package io.bpmnrepo.backend.assignment.infrastructure;

import io.bpmnrepo.backend.assignment.infrastructure.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface AssignmentJpa extends JpaRepository<AssignmentEntity, String> {

    List<AssignmentEntity> findAssignmentEntitiesByAssignmentId_UserIdEquals(String userId);

    AssignmentEntity findByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(String bpmnRepositoryId, String userId);

    @Transactional
    void deleteAssignmentEntitiesByAssignmentId_BpmnRepositoryId(String bpmnRepositoryId);
}
