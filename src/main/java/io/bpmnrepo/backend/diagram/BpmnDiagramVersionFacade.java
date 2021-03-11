package io.bpmnrepo.backend.diagram;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
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
public class BpmnDiagramVersionFacade {
    private final AuthService authService;
    private final VerifyRelationService verifyRelationService;
    private final BpmnDiagramVersionService bpmnDiagramVersionService;


    public void createOrUpdateVersion(BpmnDiagramVersionTO bpmnDiagramVersionTO) {
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnDiagramVersionTO);
        authService.checkIfOperationIsAllowed(bpmnDiagramVersionTO.getBpmnRepositoryId(), RoleEnum.MEMBER);
        bpmnDiagramVersionService.createOrUpdateVersion(bpmnDiagramVersionTO);
    }

    public List<BpmnDiagramVersionTO> getAllVersions(String bpmnRepositoryId, String bpmnDiagramId){
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.VIEWER);
        return bpmnDiagramVersionService.getAllVersions(bpmnDiagramId);
    }

    public BpmnDiagramVersionTO getLatestVersion(String bpmnRepositoryId, String bpmnDiagramId){
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.VIEWER);
        return bpmnDiagramVersionService.getLatestVersion(bpmnDiagramId);
    }

    public BpmnDiagramVersionTO getSingleVersion(String bpmnRepositoryId, String bpmnDiagramId, String bpmnDiagramVersionId){
        verifyRelationService.verifyVersionIsFromSpecifiedDiagram(bpmnDiagramId, bpmnDiagramVersionId);
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.VIEWER);
        return bpmnDiagramVersionService.getSingleVersion(bpmnDiagramVersionId);
    }
}
