package io.bpmnrepo.backend.shared;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramVersionEntity;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramJpa;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramVersionJpa;
import io.bpmnrepo.backend.shared.exception.AccessRightException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerifyRelationService {
    private final BpmnDiagramJpa bpmnDiagramJpa;
    private final BpmnDiagramVersionJpa bpmnDiagramVersionJpa;

    public void verifyDiagramIsInSpecifiedRepository(BpmnDiagramVersionTO bpmnDiagramVersionTO) {
        String bpmnRepositoryId = bpmnDiagramVersionTO.getBpmnRepositoryId();
        String bpmnDiagramId = bpmnDiagramVersionTO.getBpmnDiagramId();
        System.out.println(bpmnDiagramId);
        BpmnDiagramEntity bpmnDiagramEntity = bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId);
        if (!bpmnDiagramEntity.getBpmnRepositoryId().equals(bpmnRepositoryId)) {
            throw new AccessRightException("This Diagram does not belong to the specified Repository");
        }
    }

    public void verifyDiagramIsInSpecifiedRepository(BpmnDiagramTO bpmnDiagramTO) {
        String bpmnRepositoryId = bpmnDiagramTO.getBpmnRepositoryId();
        String bpmnDiagramId = bpmnDiagramTO.getBpmnDiagramId();
        System.out.println(bpmnDiagramId);
        BpmnDiagramEntity bpmnDiagramEntity = bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId);
        if (!bpmnDiagramEntity.getBpmnRepositoryId().equals(bpmnRepositoryId)) {
            throw new AccessRightException("This Diagram does not belong to the specified Repository");
        }
    }

    public void verifyDiagramIsInSpecifiedRepository(String bpmnRepositoryId, String bpmnDiagramId){
        BpmnDiagramEntity bpmnDiagramEntity = bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId);
        if(!bpmnDiagramEntity.getBpmnRepositoryId().equals(bpmnRepositoryId)){
            throw new AccessRightException("This Diagram does not belong to the specified Repository");
        }
    }


    public void verifyVersionIsFromSpecifiedDiagram(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        String bpmnDiagramVersionId = bpmnDiagramVersionTO.getBpmnDiagramVersionId();
        String bpmnDiagramId = bpmnDiagramVersionTO.getBpmnDiagramId();
        BpmnDiagramVersionEntity bpmnDiagramVersionEntity = bpmnDiagramVersionJpa.findAllByBpmnDiagramVersionIdEquals(bpmnDiagramVersionId);
        if(!bpmnDiagramVersionEntity.getBpmnDiagramId().equals(bpmnDiagramId)){
            throw new AccessRightException("This version does not belong to the specified Diagram");
        }
    }

    public void verifyVersionIsFromSpecifiedDiagram(String bpmnDiagramId, String bpmnDiagramVersionId) {
        BpmnDiagramVersionEntity bpmnDiagramVersionEntity = bpmnDiagramVersionJpa.findAllByBpmnDiagramVersionIdEquals(bpmnDiagramVersionId);
        if (!bpmnDiagramVersionEntity.getBpmnDiagramId().equals(bpmnDiagramId)) {
            throw new AccessRightException("This version does not belong to the specified Diagram");
        }
    }

    public void verifyVersionIsInitialVersion(String bpmnDiagramId){
        BpmnDiagramVersionEntity bpmnDiagramVersionEntity = bpmnDiagramVersionJpa.findFirstByBpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(bpmnDiagramId);
        if(bpmnDiagramVersionEntity != null){
            throw new AccessRightException(String.format("Tried to create initial version, although there are already %s versions of this Diagram present", bpmnDiagramVersionEntity.getBpmnDiagramVersionNumber().toString()));
        }
    }

    public void verifyVersionIsUpdate(BpmnDiagramVersionTO bpmnDiagramVersionTO){
        BpmnDiagramVersionEntity bpmnDiagramVersionEntity = bpmnDiagramVersionJpa.findFirstByBpmnDiagramIdOrderByBpmnDiagramVersionNumberDesc(bpmnDiagramVersionTO.getBpmnDiagramId());
        if(bpmnDiagramVersionEntity == null){
            throw new AccessRightException(String.format("No diagram found that could be updated"));
        }
        String bpmnAsXLMOld = new String(bpmnDiagramVersionEntity.getBpmnDiagramVersionFile());
        if(bpmnAsXLMOld.equals(bpmnDiagramVersionTO.getBpmnAsXML())){
            throw new AccessRightException("No changes detected, updating failed");
        }
    }
}
