package io.bpmnrepo.backend.diagram;


import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramJpa;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.VerifyRelationService;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.shared.exception.AccessRightException;
import io.bpmnrepo.backend.shared.exception.NameConflictException;
import io.bpmnrepo.backend.user.domain.business.NameAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BpmnDiagramFacade {
    private final AuthService authService;
    private final VerifyRelationService verifyRelationService;

    private final BpmnDiagramService bpmnDiagramService;
    private final BpmnDiagramVersionService bpmnDiagramVersionService;

    private final BpmnDiagramJpa bpmnDiagramJpa;


    public void createOrUpdateDiagram(String bpmnRepositoryId, BpmnDiagramTO bpmnDiagramTO){
        this.verifyRelationService.checkIfRepositoryIdsMatch(bpmnRepositoryId, bpmnDiagramTO);
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.MEMBER);
        bpmnDiagramTO.setBpmnRepositoryId(bpmnRepositoryId);
        if (bpmnDiagramTO.getBpmnDiagramId() == null || bpmnDiagramTO.getBpmnDiagramId().isEmpty()) {
            checkIfNameIsAvailable(bpmnRepositoryId, bpmnDiagramTO.getBpmnDiagramName());
            bpmnDiagramService.createDiagram(bpmnDiagramTO);
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
            throw new NameConflictException("Diagram name duplicated - please choose another name");
        }
    }

    public List<BpmnDiagramTO> getDiagramsFromRepo(String repositoryId){
        authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER);
        return bpmnDiagramService.getDiagramsFromRepo(repositoryId);
    }

    public BpmnDiagramTO getSingleDiagram(String bpmnRepositoryId, String bpmnDiagramId){
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.VIEWER);
        return bpmnDiagramService.getSingleDiagram(bpmnRepositoryId, bpmnDiagramId);
    }


        public void deleteDiagram(String bpmnRepositoryId, String bpmnDiagramId){
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.ADMIN);
        bpmnDiagramVersionService.deleteAllByDiagramId(bpmnDiagramId);
        bpmnDiagramService.deleteDiagram(bpmnRepositoryId, bpmnDiagramId);
    }

}
