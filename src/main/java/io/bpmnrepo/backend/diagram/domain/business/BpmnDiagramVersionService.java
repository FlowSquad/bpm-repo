package io.bpmnrepo.backend.diagram.domain.business;


import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagramVersion;
import io.bpmnrepo.backend.diagram.domain.mapper.VersionMapper;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramVersionEntity;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramVersionJpa;
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
    private final AuthService authService;
    private final VersionMapper mapper;



    public void createOrUpdateVersion(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        BpmnDiagramVersionEntity bpmnDiagramVersionEntity = this.bpmnDiagramVersionJpa.findFirstByBpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(bpmnDiagramVersionTO.getBpmnDiagramId());
        if (bpmnDiagramVersionEntity == null) {
            createInitialVersion(bpmnDiagramVersionTO);
        }
        else {
            updateExistingVersion(bpmnDiagramVersionEntity, bpmnDiagramVersionTO);
        }
    }


    private void createInitialVersion(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        BpmnDiagramVersion bpmnDiagramVersion = new BpmnDiagramVersion(bpmnDiagramVersionTO);
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

    public List<BpmnDiagramVersionTO> getAllVersions(String bpmnDiagramId){
        //1. Query all Versions by providing the diagram id
        //2. for all versions: map them to a TO
        return this.bpmnDiagramVersionJpa.findAllByBpmnDiagramId(bpmnDiagramId).stream()
                    .map(this.mapper::toTO)
                    .collect(Collectors.toList());
    }

    public BpmnDiagramVersionTO getLatestVersion(String bpmnDiagramId){
        BpmnDiagramVersionEntity bpmnDiagramVersionEntity = this.bpmnDiagramVersionJpa.findFirstByBpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(bpmnDiagramId);
        return this.mapper.toTO(bpmnDiagramVersionEntity);
    }

    public BpmnDiagramVersionTO getSingleVersion(String bpmnDiagramVersionId){
            return this.mapper.toTO(bpmnDiagramVersionJpa.findAllByBpmnDiagramVersionIdEquals(bpmnDiagramVersionId));
    }

    private void saveToDb(BpmnDiagramVersionEntity bpmnDiagramVersionEntity){
        authService.checkIfOperationIsAllowed(bpmnDiagramVersionEntity.getBpmnRepositoryId(), RoleEnum.MEMBER);
        bpmnDiagramVersionJpa.save(bpmnDiagramVersionEntity);
        System.out.println("Created Version");
    }


    public void deleteAllByDiagramId(String bpmnDiagramId){
        //Auth Check in Facade
        int deletedVersions = this.bpmnDiagramVersionJpa.deleteAllByBpmnDiagramId(bpmnDiagramId);
        log.debug(String.format("Deleted %s versions", deletedVersions));
    }

    public void deleteAllByRepositoryId(String bpmnRepositoryId){
        //Auth check in Facade
        int deletedVersions = this.bpmnDiagramVersionJpa.deleteAllByBpmnRepositoryId(bpmnRepositoryId);
        log.debug(String.format("Deleted %s versions", deletedVersions));
    }
}
