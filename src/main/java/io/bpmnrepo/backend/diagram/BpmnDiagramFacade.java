package io.bpmnrepo.backend.diagram;


import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.VerifyRelationService;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
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


    public void createDiagram(BpmnDiagramTO bpmnDiagramTO){
        authService.checkIfOperationIsAllowed(bpmnDiagramTO.getBpmnRepositoryId(), RoleEnum.MEMBER);
        bpmnDiagramService.createDiagram(bpmnDiagramTO);
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
