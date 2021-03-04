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



    public void createOrUpdateVersion(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        if(authService.checkIfOperationIsAllowed(bpmnDiagramVersionTO.getBpmnRepositoryId(), RoleEnum.MEMBER)) {
            System.out.println(bpmnDiagramVersionTO.getBpmnRepositoryId());
            //1: check for initial creation or update
            BpmnDiagramVersionEntity bpmnDiagramVersionEntity = this.bpmnDiagramVersionJpa.findFirstByBpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(bpmnDiagramVersionTO.getBpmnDiagramId());
            if (bpmnDiagramVersionEntity == null) {
                createInitialVersion(bpmnDiagramVersionTO);
            }
            else {
                updateExistingVersion(bpmnDiagramVersionEntity, bpmnDiagramVersionTO);
            }
        }
        else{
            throw new AccessRightException("Only Members, Admins and Owners are allowed to create new versions");
        }
    }

    private void createInitialVersion(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        BpmnDiagramVersion bpmnDiagramVersion = new BpmnDiagramVersion(bpmnDiagramVersionTO);
        System.out.println(bpmnDiagramVersion.getBpmnRepositoryId() + "repoid");
        this.saveToDb(this.mapper.toEntity(bpmnDiagramVersion));
    }

    private void updateExistingVersion(BpmnDiagramVersionEntity bpmnDiagramVersionEntity, BpmnDiagramVersionTO bpmnDiagramVersionTO){
        //use the old name if no new name is provided
        if (bpmnDiagramVersionTO.getBpmnDiagramVersionName() == null || bpmnDiagramVersionTO.getBpmnDiagramVersionName().isEmpty()) {
            bpmnDiagramVersionTO.setBpmnDiagramVersionName(bpmnDiagramVersionEntity.getBpmnDiagramVersionName());
        }
        bpmnDiagramVersionTO.setBpmnDiagramVersionNumber(bpmnDiagramVersionEntity.getBpmnDiagramVersionNumber());
        BpmnDiagramVersion bpmnDiagramVersion = new BpmnDiagramVersion((bpmnDiagramVersionTO));
        this.saveToDb(this.mapper.toEntity(bpmnDiagramVersion));
    }

    public List<BpmnDiagramVersionTO> getAllVersions(String bpmnRepositoryId, String bpmnDiagramId){
        verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        if(authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.VIEWER)) {
            //1. Query all Versions by providing the diagram id
            //2. for all versions: map them to a TO
            return this.bpmnDiagramVersionJpa.findAllByBpmnDiagramId(bpmnDiagramId).stream()
                    .map(this.mapper::toTO)
                    .collect(Collectors.toList());
        }
        else{
            throw new AccessRightException("You are not allowed to view the versions of this diagram");
        }
    }

    public BpmnDiagramVersionTO getLatestVersion(String bpmnRepositoryId, String bpmnDiagramId){
        verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        if(authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.VIEWER)) {
            BpmnDiagramVersionEntity bpmnDiagramVersionEntity = this.bpmnDiagramVersionJpa.findFirstByBpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(bpmnDiagramId);
            return this.mapper.toTO(bpmnDiagramVersionEntity);
        }
        else{
            throw new AccessRightException("You are not allowed to view the latest version of this diagram");
        }
    }

    public BpmnDiagramVersionTO getSingleVersion(String bpmnRepositoryId, String bpmnDiagramId, String bpmnDiagramVersionId){
        verifyDiagramIsInSpecifiedRepository(bpmnRepositoryId, bpmnDiagramId);
        verifyVersionIsFromSpecifiedDiagram(bpmnDiagramId, bpmnDiagramVersionId);
        if(authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.VIEWER)) {
            return this.mapper.toTO(bpmnDiagramVersionJpa.findAllByBpmnDiagramVersionIdEquals(bpmnDiagramVersionId));
        }
        else{
            throw new AccessRightException("You are not allowed to view the version of this diagram");
        }
    }

    private void saveToDb(BpmnDiagramVersionEntity bpmnDiagramVersionEntity){
        //check if user is allowed to (minimum role: MEMBER)
        if(authService.checkIfOperationIsAllowed(bpmnDiagramVersionEntity.getBpmnRepositoryId(), RoleEnum.MEMBER)) {
            bpmnDiagramVersionJpa.save(bpmnDiagramVersionEntity);
            System.out.println("Created Version");
        }
        else{
            System.out.println("Creating Version failed");
        }
    }


    public void deleteAllByRepositoryId(String bpmnRepositoryId){
            //Auth check in Facade
            int deletedVersions = this.bpmnDiagramVersionJpa.deleteAllByBpmnRepositoryId(bpmnRepositoryId);
            log.debug(String.format("Deleted %s versions", deletedVersions));
            return;

    }

    //______________________ Helper ___________________________

    //move to Facade
    public void verifyDiagramIsInSpecifiedRepository(String bpmnRepositoryId, String bpmnDiagramId){
        BpmnDiagramEntity bpmnDiagramEntity = bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId);
        if(!bpmnDiagramEntity.getBpmnRepositoryId().equals(bpmnRepositoryId)){
            throw new AccessRightException("Initiating self destruction");
        }
    }

    public void verifyVersionIsFromSpecifiedDiagram(String bpmnDiagramId, String bpmnDiagramVersionId){
        BpmnDiagramVersionEntity bpmnDiagramVersionEntity = bpmnDiagramVersionJpa.findAllByBpmnDiagramVersionIdEquals(bpmnDiagramVersionId);
        if(!bpmnDiagramVersionEntity.getBpmnDiagramId().equals(bpmnDiagramId)){
            throw new AccessRightException("This version does not belong to the specified Diagram");
        }
    }
}
