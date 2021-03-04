package io.bpmnrepo.backend.diagram.domain.business;


import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagramVersion;
import io.bpmnrepo.backend.diagram.domain.mapper.VersionMapper;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramVersionEntity;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.shared.exception.AccessRightException;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramJpa;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramVersionJpa;
import io.bpmnrepo.backend.repository.infrastructure.repository.BpmnRepoJpa;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BpmnDiagramVersionService {

    private final BpmnDiagramVersionJpa bpmnDiagramVersionJpa;
    private final BpmnDiagramJpa bpmnDiagramJpa;
    private final BpmnRepoJpa bpmnRepoJpa;
    private final AuthService authService;
    private final BpmnDiagramService bpmnDiagramService;
    private final VersionMapper mapper;



    public void createNewVersion(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        if(authService.checkIfOperationIsAllowed(getRepositoryIdByDiagramId(bpmnDiagramVersionTO.getBpmnDiagramId()), RoleEnum.MEMBER)) {
            BpmnDiagramVersionEntity bpmnDiagramVersionEntity = this.bpmnDiagramVersionJpa.findFirstByBpmnDiagramEntity_BpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(bpmnDiagramVersionTO.getBpmnDiagramId());
            if (bpmnDiagramVersionEntity == null) {
                BpmnDiagramVersion bpmnDiagramVersion = new BpmnDiagramVersion(bpmnDiagramVersionTO);
                this.saveToDb(this.mapper.toEntity(bpmnDiagramVersion, this.getBpmnDiagramEntityById(bpmnDiagramVersionTO.getBpmnDiagramId())));
            }
            //else clause handles update of version fetches versionnumber (and name, if not provided) from old version and persists a new version
            else {
                if (bpmnDiagramVersionTO.getBpmnDiagramVersionName() == null || bpmnDiagramVersionTO.getBpmnDiagramVersionName().isEmpty()) {
                    bpmnDiagramVersionTO.setBpmnDiagramVersionName(bpmnDiagramVersionEntity.getBpmnDiagramVersionName());
                }
                bpmnDiagramVersionTO.setBpmnDiagramVersionNumber(bpmnDiagramVersionEntity.getBpmnDiagramVersionNumber());
                BpmnDiagramVersion bpmnDiagramVersion = new BpmnDiagramVersion((bpmnDiagramVersionTO));
                this.saveToDb(this.mapper.toEntity(bpmnDiagramVersion, this.getBpmnDiagramEntityById(bpmnDiagramVersionTO.getBpmnDiagramId())));
            }
        }
        else{
            throw new AccessRightException("Only Members, Admins and Owners are allowed to create new versions");
        }
    }

    public List<BpmnDiagramVersionTO> getAllVersions(String bpmndiagramId){
        if(authService.checkIfOperationIsAllowed(getRepositoryIdByDiagramId(bpmndiagramId), RoleEnum.VIEWER)) {
            //1. Query all Versions by providing the diagram id
            //2. for all versions: map them to a TO
            return this.bpmnDiagramVersionJpa.findAllByBpmnDiagramEntity_BpmnDiagramId(bpmndiagramId).stream()
                    .map(this.mapper::toTO)
                    .collect(Collectors.toList());
        }
        else{
            return null;
        }
    }

    public BpmnDiagramVersionTO getLatestVersion(String bpmnDiagramId){
        if(authService.checkIfOperationIsAllowed(getRepositoryIdByDiagramId(bpmnDiagramId), RoleEnum.VIEWER));
        BpmnDiagramVersionEntity bpmnDiagramVersionEntity = this.bpmnDiagramVersionJpa.findFirstByBpmnDiagramEntity_BpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(bpmnDiagramId);
        return this.mapper.toTO(bpmnDiagramVersionEntity);
    }

    public BpmnDiagramVersionTO getSingleVersion(String bpmnDiagramVersionId){
        if(authService.checkIfOperationIsAllowed(getRepositoryIdByVersionId(bpmnDiagramVersionId), RoleEnum.VIEWER)) {
            return this.mapper.toTO(bpmnDiagramVersionJpa.findAllByBpmnDiagramVersionIdEquals(bpmnDiagramVersionId));
        }
        else{
            return null;
        }
    }

    public void saveToDb(BpmnDiagramVersionEntity entity){
        //check if user is allowed to (minimum role: MEMBER)
        System.out.println("Diagram version id:");
        System.out.println(entity.getBpmnDiagramVersionId());
        if(authService.checkIfOperationIsAllowed(getRepositoryIdByDiagramId(entity.getBpmnDiagramEntity().getBpmnDiagramId()), RoleEnum.MEMBER)) {
            bpmnDiagramVersionJpa.save(entity);
            System.out.println("Created Version");
        }
        else{
            System.out.println("Creating Version failed");
        }
    }




    //______________________ Helper ___________________________

    //get the corresponding BpmnDiagramEntity
    public BpmnDiagramEntity getBpmnDiagramEntityById(String bpmnDiagramId) {
        return bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId);
    }

    public String getRepositoryIdByVersionId(String versionId){
        System.out.println(bpmnDiagramVersionJpa.findAllByBpmnDiagramVersionIdEquals(versionId));
        return getRepositoryIdByDiagramId(bpmnDiagramVersionJpa.findAllByBpmnDiagramVersionIdEquals(versionId).getBpmnDiagramEntity().getBpmnDiagramId());
    }

    public String getRepositoryIdByDiagramId(String diagramId){
        return bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(diagramId).getBpmnDiagramRepository().getBpmnRepositoryId();
    }


}
