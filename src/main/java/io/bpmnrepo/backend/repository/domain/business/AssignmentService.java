package io.bpmnrepo.backend.repository.domain.business;

import io.bpmnrepo.backend.repository.api.transport.AssignmentWithUserNameTO;
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


    public void createOrUpdateAssignment(AssignmentWithUserNameTO assignmentWithUserNameTO){
        this.authService.checkIfOperationIsAllowed(assignmentWithUserNameTO.getBpmnRepositoryId(), RoleEnum.ADMIN);
        String newAssignmentUserId = this.userService.getUserIdByEmail(assignmentWithUserNameTO.getUserName());

        AssignmentTO assignmentTO = new AssignmentTO(assignmentWithUserNameTO.getBpmnRepositoryId(), userService.getUserIdByEmail(assignmentWithUserNameTO.getUserName()), assignmentWithUserNameTO.getRoleEnum());
        AssignmentEntity assignmentEntity = assignmentJpa.findByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(assignmentTO.getBpmnRepositoryId(), newAssignmentUserId);
        if(assignmentEntity == null){
            createAssignment(assignmentTO);
            log.debug("Created assignment");
        }
        else{
            updateAssignment(assignmentTO);
            log.debug(String.format("Updated role to %s", assignmentTO.getRoleEnum().toString()));
        }
    }


    public void createAssignment(AssignmentTO assignmentTO){
        Assignment assignment = this.mapper.toModel(assignmentTO);
        AssignmentEntity assignmentEntity = this.mapper.toEntity(assignment, this.mapper.toEmbeddable(assignment.getUserId(), assignment.getBpmnRepositoryId()));
        this.saveToDb(assignmentEntity);
    }

    //0: Owner, 1: Admin, 2: Member, 3: Viewer
    public void updateAssignment(AssignmentTO assignmentTO) {
        //Assignments can be managed by Admins and Owners
        String newAssignmentUserId = assignmentTO.getUserId();
        String currentUserId = this.userService.getUserIdOfCurrentUser();
        this.authService.checkIfOperationIsAllowed(assignmentTO.getBpmnRepositoryId(), RoleEnum.ADMIN);

        RoleEnum currentUserRole = this.getUserRole(assignmentTO.getBpmnRepositoryId(), currentUserId);
        RoleEnum affectedUserRole = this.getUserRole(assignmentTO.getBpmnRepositoryId(), newAssignmentUserId);

        //Exception if the user tries to change its own rights
        if (newAssignmentUserId == currentUserId) {
            throw new AccessRightException("You can't change your own role");
        }
        //Exception if the user tries to change the role of someone with higher permissions (if an admin tries to change the role of an owner)
        if(affectedUserRole.ordinal() < currentUserRole.ordinal()){
            throw new AccessRightException(String.format("You cant change the role of %s because your role provides less rights (You are an \"%s\")", this.getUserRole(assignmentTO.getBpmnRepositoryId(), newAssignmentUserId), this.getUserRole(assignmentTO.getBpmnRepositoryId(), this.userService.getUserIdOfCurrentUser())));
        }
        //Exception if the user tries to give someone a role that is higher than its own
        if(currentUserRole.ordinal() > assignmentTO.getRoleEnum().ordinal()){
            throw new AccessRightException("You can't assign roles with higher permissions than your own");
        }
        Assignment assignment = new Assignment(assignmentTO);
        AssignmentEntity assignmentEntity = this.mapper.toEntity(assignment, this.mapper.toEmbeddable(assignment.getUserId(), assignment.getBpmnRepositoryId()));
        this.saveToDb(assignmentEntity);
    }


    public void createInitialAssignment(String bpmnRepositoryId){
        String currentUser = this.userService.getUserIdOfCurrentUser();
        AssignmentTO assignmentTO = new AssignmentTO(bpmnRepositoryId, currentUser, RoleEnum.OWNER);
        Assignment assignment = new Assignment(assignmentTO);
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
    }


    public void deleteAssignment(AssignmentWithUserNameTO assignmentWithUserNameTO){
        String assignmentUserId = this.userService.getUserIdByEmail(assignmentWithUserNameTO.getUserName());
        String currentUserId = this.userService.getUserIdOfCurrentUser();
        this.authService.checkIfOperationIsAllowed(assignmentWithUserNameTO.getBpmnRepositoryId(), RoleEnum.ADMIN);

        if(this.getUserRole(assignmentWithUserNameTO.getBpmnRepositoryId(), assignmentUserId).ordinal() < this.getUserRole(assignmentWithUserNameTO.getBpmnRepositoryId(), currentUserId).ordinal()){
            throw new AccessRightException(String.format("You cant remove %s from this repository because your role provides less rights (You are an %s)",
                    this.getUserRole(assignmentWithUserNameTO.getBpmnRepositoryId(), assignmentUserId),
                    this.getUserRole(assignmentWithUserNameTO.getBpmnRepositoryId(), this.userService.getUserIdOfCurrentUser())));
        }

        this.assignmentJpa.deleteAssignmentEntityByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(assignmentWithUserNameTO.getBpmnRepositoryId(), assignmentUserId);
    }


    public void deleteAllByRepositoryId(String bpmnRepositoryId){
        //Auth check in Facade
        int deletedAssignments = this.assignmentJpa.deleteAllByAssignmentId_BpmnRepositoryId(bpmnRepositoryId);
        log.debug(String.format("Deleted Assignments for all %s users", deletedAssignments));
    }
}
