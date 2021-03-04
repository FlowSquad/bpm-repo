package io.bpmnrepo.backend.diagram;


import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.shared.exception.AccessRightException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BpmnDiagramFacade {

    private final BpmnDiagramService bpmnDiagramService;
    private final BpmnDiagramVersionService bpmnDiagramVersionService;
    private final AuthService authService;

    public void deleteDiagram(String bpmnRepositoryId, String bpmnDiagramId){
        if(this.authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.ADMIN)){
            bpmnDiagramVersionService.deleteAllByRepositoryId(bpmnRepositoryId);
            bpmnDiagramService.deleteDiagram(bpmnRepositoryId, bpmnDiagramId);
        }
        else{
            throw new AccessRightException("Only Admins and Owners are allowed to delete diagrams");
        }
    }

}
