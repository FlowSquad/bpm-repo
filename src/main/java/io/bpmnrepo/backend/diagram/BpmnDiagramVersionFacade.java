package io.bpmnrepo.backend.diagram;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionUploadTO;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
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


    public String createOrUpdateVersion(String bpmnRepositoryId, String bpmnDiagramId, BpmnDiagramVersionUploadTO bpmnDiagramVersionUploadTO){
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.MEMBER);
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        BpmnDiagramVersionTO bpmnDiagramVersionTO = adoptProperties(bpmnRepositoryId, bpmnDiagramId, bpmnDiagramVersionUploadTO);
        //true if it is the initial version
        if(verifyRelationService.checkIfVersionIsInitialVersion(bpmnDiagramId)){
            String bpmnDiagramVersionId = this.bpmnDiagramVersionService.createInitialVersion(bpmnDiagramVersionTO);
            return bpmnDiagramVersionId;
        }
        else{
            String bpmnDiagramVersionId = this.bpmnDiagramVersionService.updateVersion(bpmnDiagramVersionTO);
            deleteAutosavedVersionsIfReleaseOrMilestoneIsSaved(bpmnRepositoryId, bpmnDiagramId, bpmnDiagramVersionUploadTO.getSaveType());
            return bpmnDiagramVersionId;
        }
    }


    //simply deletes all entities that contain the SaveType "AUTOSAVE"
    private void deleteAutosavedVersionsIfReleaseOrMilestoneIsSaved(String bpmnRepositoryId, String bpmnDiagramId, SaveTypeEnum saveTypeEnum) {
        if(saveTypeEnum != SaveTypeEnum.AUTOSAVE){
            this.bpmnDiagramVersionService.deleteAutosavedVersions(bpmnRepositoryId, bpmnDiagramId);
        }


    }

    public BpmnDiagramVersionTO adoptProperties(String bpmnRepositoryId, String bpmnDiagramId, BpmnDiagramVersionUploadTO bpmnDiagramVersionUploadTO){
        BpmnDiagramVersionTO bpmnDiagramVersionTO = new BpmnDiagramVersionTO();
        bpmnDiagramVersionTO.setBpmnRepositoryId(bpmnRepositoryId);
        bpmnDiagramVersionTO.setBpmnDiagramId(bpmnDiagramId);
        bpmnDiagramVersionTO.setBpmnAsXML(bpmnDiagramVersionUploadTO.getBpmnAsXML());
        bpmnDiagramVersionTO.setBpmnDiagramVersionComment(bpmnDiagramVersionUploadTO.getBpmnDiagramVersionComment());
        bpmnDiagramVersionTO.setSaveType(bpmnDiagramVersionUploadTO.getSaveType());
        return bpmnDiagramVersionTO;
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

    //Check if it's really an initial version or an update
/*    public void createInitialVersion(String bpmnRepositoryId, String bpmnDiagramId, BpmnDiagramVersionUploadTO bpmnDiagramVersionUploadTO){
        authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.MEMBER);
        verifyRelationService.verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);

        verifyRelationService.verifyVersionIsInitialVersion(bpmnDiagramId);
        bpmnDiagramVersionService.createInitialVersion(bpmnDiagramVersionUploadTO);
    }*/

/*    public void updateVersion(String bpmnRepositoryId, String bpmnDiagramId, BpmnDiagramVersionUploadTO bpmnDiagramVersionUploadTO) {
        bpmnDiagramVersionService.updateVersion(bpmnDiagramVersionUploadTO);
    }*/
}
