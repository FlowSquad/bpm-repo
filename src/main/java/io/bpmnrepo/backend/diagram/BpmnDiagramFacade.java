package io.bpmnrepo.backend.diagram;


import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramSVGUploadTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramUploadTO;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.bpmnrepo.backend.diagram.domain.business.StarredService;
import io.bpmnrepo.backend.diagram.domain.exception.DiagramNameAlreadyInUseException;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.diagram.infrastructure.entity.StarredEntity;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramJpa;
import io.bpmnrepo.backend.diagram.infrastructure.repository.StarredJpa;
import io.bpmnrepo.backend.repository.domain.business.AssignmentService;
import io.bpmnrepo.backend.repository.domain.business.BpmnRepositoryService;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.VerifyRelationService;
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
public class BpmnDiagramFacade {
    private final AuthService authService;
    private final VerifyRelationService verifyRelationService;
    private final UserService userService;

    private final BpmnDiagramService bpmnDiagramService;
    private final BpmnDiagramVersionService bpmnDiagramVersionService;
    private final StarredService starredService;

    private final BpmnDiagramJpa bpmnDiagramJpa;
    private final StarredJpa starredJpa;

    private final AssignmentService assignmentService;
    private final BpmnRepositoryService bpmnRepositoryService;


    public void createOrUpdateDiagram(String bpmnRepositoryId, BpmnDiagramUploadTO bpmnDiagramUploadTO){
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.MEMBER);
        BpmnDiagramTO bpmnDiagramTO = new BpmnDiagramTO(bpmnRepositoryId, bpmnDiagramUploadTO);
        if (bpmnDiagramTO.getBpmnDiagramId() == null || bpmnDiagramTO.getBpmnDiagramId().isEmpty()) {
            checkIfNameIsAvailable(bpmnRepositoryId, bpmnDiagramTO.getBpmnDiagramName());
            bpmnDiagramService.createDiagram(bpmnDiagramTO);
            Integer existingDiagrams = this.bpmnDiagramService.countExistingDiagrams(bpmnRepositoryId);
            bpmnRepositoryService.updateExistingDiagrams(bpmnRepositoryId, existingDiagrams);
            log.debug("Diagram created");
        }
        else{
            verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnDiagramTO);
            bpmnDiagramService.updateDiagram(bpmnDiagramTO);

            log.debug("Diagram updated");
        }
    }

    public void checkIfNameIsAvailable(String bpmnRepositoryId, String bpmnDiagramName){
        if(bpmnDiagramJpa.findBpmnDiagramEntityByBpmnRepositoryIdAndBpmnDiagramName(bpmnRepositoryId, bpmnDiagramName) !=  null){
            throw new DiagramNameAlreadyInUseException();
        }
    }

    public List<BpmnDiagramTO> getDiagramsFromRepo(String repositoryId){
        authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER);
        return bpmnDiagramService.getDiagramsFromRepo(repositoryId);
    }

    public BpmnDiagramTO getSingleDiagram(String bpmnRepositoryId, String bpmnDiagramId){
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.VIEWER);
        return bpmnDiagramService.getSingleDiagram(bpmnDiagramId);
    }

    public List<BpmnDiagramTO> getRecent() {
       // List<Assignment> assignments = this.assignmentJpa.findAssignmentEntitiesByAssignmentId_UserIdEquals(this.userService.getUserIdOfCurrentUser());
        List<String> assignments = this.assignmentService.getAllAssignedRepositoryIds(this.userService.getUserIdOfCurrentUser());
        //pass assignments to diagramService and return all diagramTOs
        return this.bpmnDiagramService.getRecent(assignments);
    }


    public void updatePreviewSVG(String bpmnRepositoryId, String bpmnDiagramId, BpmnDiagramSVGUploadTO bpmnDiagramSVGUploadTO) {
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.MEMBER);
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        this.bpmnDiagramService.updatePreviewSVG(bpmnDiagramId, bpmnDiagramSVGUploadTO);

    }


        public void deleteDiagram(String bpmnRepositoryId, String bpmnDiagramId){
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.ADMIN);
        bpmnDiagramVersionService.deleteAllByDiagramId(bpmnDiagramId);
        bpmnDiagramService.deleteDiagram(bpmnDiagramId);
        Integer existingDiagrams = this.bpmnDiagramService.countExistingDiagrams(bpmnRepositoryId);
        bpmnRepositoryService.updateExistingDiagrams(bpmnRepositoryId, existingDiagrams);

    }


    public void setStarred(String bpmnDiagramId) {
        BpmnDiagramEntity bpmnDiagramEntity = this.bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId);
        authService.checkIfOperationIsAllowed(bpmnDiagramEntity.getBpmnRepositoryId(), RoleEnum.VIEWER);
        String currentUserId = this.userService.getUserIdOfCurrentUser();
        StarredEntity starredEntity = this.starredJpa.findByStarredId_BpmnDiagramIdAndStarredId_UserId(bpmnDiagramId, currentUserId);
        if(starredEntity == null){
            this.starredService.createStarred(bpmnDiagramId, currentUserId);
        }
        else{
            this.starredService.deleteStarred(bpmnDiagramId, currentUserId);
        }
    }

    public List<BpmnDiagramTO> getStarred() {
        String currentUserId = this.userService.getUserIdOfCurrentUser();
        List<StarredEntity> starredList = this.starredService.getStarred(currentUserId);
        return starredList.stream()
                .map(starredEntity -> this.bpmnDiagramService.getSingleDiagram(starredEntity.getStarredId().getBpmnDiagramId()))
                .collect(Collectors.toList());

    }
}
