package io.bpmnrepo.backend.diagram.domain.business;

import io.bpmnrepo.backend.diagram.domain.mapper.DiagramMapper;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagram;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramJpa;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BpmnDiagramService {

    private final BpmnDiagramJpa bpmnDiagramJpa;
    private final DiagramMapper mapper;
    private final AuthService authService;


    public void createDiagram(BpmnDiagramTO bpmnDiagramTO){
        BpmnDiagram bpmnDiagram = new BpmnDiagram(bpmnDiagramTO);
        saveToDb(this.mapper.toEntity(bpmnDiagram));
    }

    public void updateDiagram(BpmnDiagramTO bpmnDiagramTO){
        BpmnDiagramEntity bpmnDiagramEntity = this.bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramTO.getBpmnDiagramId());
        if(bpmnDiagramTO.getBpmnDiagramName() != null || !bpmnDiagramTO.getBpmnDiagramName().isEmpty()){
            bpmnDiagramEntity.setBpmnDiagramName(bpmnDiagramTO.getBpmnDiagramName());
        }
        if(bpmnDiagramTO.getBpmnDiagramDescription() != null || !bpmnDiagramTO.getBpmnDiagramDescription().isEmpty()){
            bpmnDiagramEntity.setBpmnDiagramDescription(bpmnDiagramTO.getBpmnDiagramDescription());
        }
        saveToDb(bpmnDiagramEntity);
    }


    public List<BpmnDiagramTO> getDiagramsFromRepo(String repositoryId){
        return this.bpmnDiagramJpa.findBpmnDiagramEntitiesByBpmnRepositoryId(repositoryId).stream()
                .map(this.mapper::toTO)
                .collect(Collectors.toList());
    }


    public BpmnDiagramTO getSingleDiagram(String bpmnRepositoryId, String bpmnDiagramId){
        return this.mapper.toTO(this.bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId));
    }



    private void saveToDb(BpmnDiagramEntity bpmnDiagramEntity){
        authService.checkIfOperationIsAllowed(bpmnDiagramEntity.getBpmnRepositoryId(), RoleEnum.MEMBER);
        bpmnDiagramJpa.save(bpmnDiagramEntity);

    }



    public void deleteDiagram(String bpmnRepositoryId, String bpmnDiagramId){
        int deletedDiagrams = this.bpmnDiagramJpa.deleteBpmnDiagramEntitiyByBpmnDiagramId(bpmnDiagramId);
        log.info(String.format("Deleted %s Diagram", deletedDiagrams));
    }


    public void deleteAllByRepositoryId(String bpmnRepositoryId){
        //Auth check performed in Facade
        int deletedDiagrams = this.bpmnDiagramJpa.deleteAllByBpmnRepositoryId(bpmnRepositoryId);
        log.debug(String.format("Deleted %s diagrams", deletedDiagrams));

    }
}
