package io.bpmnrepo.backend.shared;

import io.bpmnrepo.backend.assignment.infrastructure.AssignmentEntity;
import io.bpmnrepo.backend.assignment.infrastructure.AssignmentJpa;
import io.bpmnrepo.backend.user.domain.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AssignmentJpa assignmentJpa;

    public boolean checkIfOperationIsAllowed(String bpmnRepositoryId, RoleEnum minimumRequiredRole){
        final String userId = this.userService.getUserIdOfCurrentUser();
        AssignmentEntity assignmentEntity = this.assignmentJpa.findByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(bpmnRepositoryId, userId);
        //ExceptionHandling: if assignmentEntity is null, the user is completely missing the assignment -> no role in the repository at all
        if(assignmentEntity == null){
            throw new AccessRightException("You are not assigned to this repository");
        }
        else{
            RoleEnum roleEnum = assignmentEntity.getRoleEnum();
            //0: OWNER - 1: ADMIN 2: MEMBER 3: VIEWER
            if(minimumRequiredRole.ordinal() >= roleEnum.ordinal()){
                System.out.println("PERMISSION GRANTED");
                return true;
            }
            else{
                System.out.println("PERMISSION DENIED: Required role for this operation: \"" + minimumRequiredRole + "\" - Your role is: \"" + roleEnum.toString() + "\"");
                return false;
            }

        }
    }
}
