package io.bpmnrepo.backend.repository;

import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.api.transport.NewBpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.business.AssignmentService;
import io.bpmnrepo.backend.repository.domain.business.BpmnRepositoryService;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import io.bpmnrepo.backend.repository.infrastructure.repository.AssignmentJpa;
import io.bpmnrepo.backend.repository.infrastructure.repository.BpmnRepoJpa;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.user.domain.business.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
    private final BpmnRepoJpa bpmnRepoJpa;
    private final AssignmentJpa assignmentJpa;

    public void createOrUpdateRepository(NewBpmnRepositoryTO newBpmnRepositoryTO){
        checkIfRepositoryNameIsAvailable(newBpmnRepositoryTO.getBpmnRepositoryName());
        String bpmnRepositoryId = this.bpmnRepositoryService.createRepository(newBpmnRepositoryTO);
        this.assignmentService.createInitialAssignment(bpmnRepositoryId);
        log.debug("Successfully created new repository");
    }

    public void updateRepository(String bpmnRepositoryId, NewBpmnRepositoryTO newBpmnRepositoryTO) {
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.ADMIN);
        this.bpmnRepositoryService.updateRepository(bpmnRepositoryId, newBpmnRepositoryTO);
        log.debug("The repository has been updated");
    }

    private void checkIfRepositoryNameIsAvailable(String bpmnRepositoryName) {
        List<AssignmentEntity> assignmentList = this.assignmentJpa.findAssignmentEntitiesByAssignmentId_UserIdEquals(this.userService.getUserIdOfCurrentUser());
        for (AssignmentEntity assignmentEntity : assignmentList) {
            Optional<BpmnRepositoryEntity> assignedRepository = this.bpmnRepoJpa.findByBpmnRepositoryIdEquals(assignmentEntity.getAssignmentId().getBpmnRepositoryId());
            if(assignedRepository.isPresent()){
                if(assignedRepository.get().getBpmnRepositoryName().equals(bpmnRepositoryName)){
                    log.warn("You are member of a repository with the same name (just a warning, no error)");
                }
            }
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
        log.debug("Deleted repository including related diagrams and assignments");
    }
}
