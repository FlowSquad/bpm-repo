package io.bpmnrepo.backend.repository;

import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.business.AssignmentService;
import io.bpmnrepo.backend.repository.domain.business.BpmnRepositoryService;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.user.domain.business.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class BpmnRepositoryFacade {
    private final BpmnRepositoryService bpmnRepositoryService;
    private final BpmnDiagramService bpmnDiagramService;
    private final AssignmentService assignmentService;
    private final UserService userService;
    private final AuthService authService;
    private final BpmnDiagramVersionService bpmnDiagramVersionService;

    public void createOrUpdateRepository(BpmnRepositoryTO bpmnRepositoryTO){
        if(bpmnRepositoryTO.getBpmnRepositoryId() == null || bpmnRepositoryTO.getBpmnRepositoryId().isBlank()){
            String bpmnRepositoryId = this.bpmnRepositoryService.createRepository(bpmnRepositoryTO);
            this.assignmentService.createInitialAssignment(bpmnRepositoryId);
            log.debug("Created new repository");
        }
        else{
            authService.checkIfOperationIsAllowed(bpmnRepositoryTO.getBpmnRepositoryId(), RoleEnum.ADMIN);
            this.bpmnRepositoryService.updateRepository(bpmnRepositoryTO);
            log.debug("The repository has been updated");
        }
    }

    public BpmnRepositoryTO getSingleRepository(String repositoryId){
        authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER);
        return bpmnRepositoryService.getSingleRepository(repositoryId);
    }


    public List<BpmnRepositoryTO> getAllRepositories(){
    final String userId = this.userService.getUserIdOfCurrentUser();
    return this.assignmentService.getAllAssignedRepositoryIds(userId).stream()
            .map(bpmnRepositoryId -> bpmnRepositoryService.getSingleRepository(bpmnRepositoryId))
            .collect(Collectors.toList());
}

    public void deleteRepository(String bpmnRepositoryId){
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.OWNER);
        this.bpmnDiagramVersionService.deleteAllByRepositoryId(bpmnRepositoryId);
        this.bpmnDiagramService.deleteAllByRepositoryId(bpmnRepositoryId);
        this.bpmnRepositoryService.deleteRepository(bpmnRepositoryId);
        this.assignmentService.deleteAllByRepositoryId(bpmnRepositoryId);
        log.debug("Wiped out the parent with all its children");
    }
}
