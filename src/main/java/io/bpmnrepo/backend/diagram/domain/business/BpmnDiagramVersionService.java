package io.bpmnrepo.backend.diagram.domain.business;


import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionUploadTO;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagramVersion;
import io.bpmnrepo.backend.diagram.domain.mapper.VersionMapper;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagramVersionUpload;
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

    public void updateVersion(BpmnDiagramVersionTO bpmnDiagramVersionTO) {
        BpmnDiagramVersionEntity bpmnDiagramVersionEntity = this.bpmnDiagramVersionJpa.findFirstByBpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(bpmnDiagramVersionTO.getBpmnDiagramId());
        //use the old comment if no new name is provided
        if (bpmnDiagramVersionTO.getBpmnDiagramVersionComment() == null || bpmnDiagramVersionTO.getBpmnDiagramVersionComment().isEmpty()) {
            bpmnDiagramVersionTO.setBpmnDiagramVersionComment(bpmnDiagramVersionEntity.getBpmnDiagramVersionComment());
        }
        bpmnDiagramVersionTO.setBpmnDiagramVersionNumber(bpmnDiagramVersionEntity.getBpmnDiagramVersionNumber());
        BpmnDiagramVersion bpmnDiagramVersion = new BpmnDiagramVersion((bpmnDiagramVersionTO));
        this.saveToDb(this.mapper.toEntity(bpmnDiagramVersion));

    }


    public void createInitialVersion(BpmnDiagramVersionUploadTO bpmnDiagramVersionUploadTO){
        //element that contains repoid, digramId and file as bytearray
        BpmnDiagramVersionUpload bpmnDiagramVersionUpload = new BpmnDiagramVersionUpload(bpmnDiagramVersionUploadTO);
        BpmnDiagramVersion bpmnDiagramVersion = new BpmnDiagramVersion(bpmnDiagramVersionUpload);
        this.saveToDb(this.mapper.toEntity(bpmnDiagramVersion));

    }

    private void updateExistingVersion(BpmnDiagramVersionEntity bpmnDiagramVersionEntity, BpmnDiagramVersionTO bpmnDiagramVersionTO) {
        //use the old name if no new name is provided
        if (bpmnDiagramVersionTO.getBpmnDiagramVersionComment() == null || bpmnDiagramVersionTO.getBpmnDiagramVersionComment().isEmpty()) {
            bpmnDiagramVersionTO.setBpmnDiagramVersionComment(bpmnDiagramVersionEntity.getBpmnDiagramVersionComment());
        }
        bpmnDiagramVersionTO.setBpmnDiagramVersionNumber(bpmnDiagramVersionEntity.getBpmnDiagramVersionNumber());
        BpmnDiagramVersion bpmnDiagramVersion = new BpmnDiagramVersion((bpmnDiagramVersionTO));
        try {
            this.saveToDb(this.mapper.toEntity(bpmnDiagramVersion));
        }
        catch(Exception e){
            e.printStackTrace();
        }
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
        log.debug("Saving successful");
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
