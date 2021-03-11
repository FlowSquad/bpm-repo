package io.bpmnrepo.backend.repository.domain.business;

import io.bpmnrepo.backend.repository.domain.mapper.AssignmentMapper;
import io.bpmnrepo.backend.repository.domain.model.Assignment;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentEntity;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.shared.exception.AccessRightException;
import io.bpmnrepo.backend.repository.infrastructure.repository.AssignmentJpa;
import io.bpmnrepo.backend.repository.api.transport.AssignmentTO;
import io.bpmnrepo.backend.user.domain.business.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentJpa assignmentJpa;
    private final AuthService authService;
    private final UserService userService;
    private final AssignmentMapper mapper;
    //method that checks if an Assignment has to be updated or created

    public void createAssignment(AssignmentTO assignmentTO) {
        //Assign the creator as Owner of the new repo
        String userId = this.userService.getUserIdByUserName(assignmentTO.getUserName());
        this.authService.checkIfOperationIsAllowed(assignmentTO.getBpmnRepositoryId(), RoleEnum.ADMIN);

        //Exception if the user tries to change its own rights
        if (userId == this.userService.getUserIdOfCurrentUser()) {
            throw new AccessRightException("You can't change your own role");
        }
        //Exception if the user tries to change the role of someone with higher permissions (if an admin tries to change the role of an owner)
        if(this.getUserRole(assignmentTO.getBpmnRepositoryId(), userId).ordinal() < this.getUserRole(assignmentTO.getBpmnRepositoryId(), this.userService.getUserIdOfCurrentUser()).ordinal()){
            throw new AccessRightException("You cant change the role of " + this.getUserRole(assignmentTO.getBpmnRepositoryId(), userId) + " because your role provides less rights (You are an " + this.getUserRole(assignmentTO.getBpmnRepositoryId(), this.userService.getUserIdOfCurrentUser()) + ")");
        }
        //Exception if the user tries to give someone a role that is higher than its own
        if(this.getUserRole(assignmentTO.getBpmnRepositoryId(), this.userService.getUserIdOfCurrentUser()).ordinal() > assignmentTO.getRoleEnum().ordinal()){
            throw new AccessRightException("You can't assign roles with higher permissions than your own");
        }
        Assignment assignment = new Assignment(userId, assignmentTO.getBpmnRepositoryId(), assignmentTO.getRoleEnum());
        AssignmentEntity assignmentEntity = this.mapper.toEntity(assignment, this.mapper.toEmbeddable(assignment.getUserId(), assignment.getBpmnRepositoryId()));
        this.saveToDb(assignmentEntity);
    }


    public void createInitialAssignment(String bpmnRepositoryId){
        String currentUser = this.userService.getUserIdOfCurrentUser();
        Assignment assignment = new Assignment(currentUser, bpmnRepositoryId, RoleEnum.OWNER);
        AssignmentEntity assignmentEntity = this.mapper.toEntity(assignment, this.mapper.toEmbeddable(currentUser, bpmnRepositoryId));
        this.assignmentJpa.save(assignmentEntity);
    }

    //receive all AssignmentEntities related to the user
    public List<String> getAllAssignedRepositoryIds(String userId){
        return this.assignmentJpa.findAssignmentEntitiesByAssignmentId_UserIdEquals(userId).stream()
                .map(assignmentEntity -> assignmentEntity.getAssignmentId().getBpmnRepositoryId())
                .collect(Collectors.toList());
    }


    public AssignmentEntity getAssignmentEntity(String bpmnRepositoryId, String userId){
        return this.assignmentJpa.findByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(bpmnRepositoryId, userId);
    }


    public RoleEnum getUserRole(String bpmnRepositoryId, String userId){
        return this.getAssignmentEntity(bpmnRepositoryId, userId).getRoleEnum();
    }


    public void saveToDb(AssignmentEntity assignmentEntity){
        authService.checkIfOperationIsAllowed(assignmentEntity.getAssignmentId().getBpmnRepositoryId(), RoleEnum.ADMIN);
        assignmentJpa.save(assignmentEntity);
        log.debug("Created Assignment");
    }

    public void deleteAllByRepositoryId(String bpmnRepositoryId){
        //Auth check in Facade
        int deletedAssignments = this.assignmentJpa.deleteAllByAssignmentId_BpmnRepositoryId(bpmnRepositoryId);
        log.debug(String.format("Deleted Assignments for all %s users", deletedAssignments));
    }
}
