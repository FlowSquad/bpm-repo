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
        BpmnDiagram bpmnDiagram = this.mapper.toModel(bpmnDiagramTO);
        //BpmnDiagram bpmnDiagram = new BpmnDiagram(bpmnDiagramTO);
        saveToDb(bpmnDiagram);
    }

    public void updateDiagram(BpmnDiagramTO bpmnDiagramTO){
        BpmnDiagramEntity bpmnDiagramEntity = this.bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramTO.getBpmnDiagramId());
        BpmnDiagram bpmnDiagram = this.mapper.toModel(bpmnDiagramEntity);
        bpmnDiagram.updateDiagram(bpmnDiagramTO);
        saveToDb(bpmnDiagram);
    }


    public List<BpmnDiagramTO> getDiagramsFromRepo(String repositoryId){
        return this.bpmnDiagramJpa.findBpmnDiagramEntitiesByBpmnRepositoryId(repositoryId).stream()
                .map(this.mapper::toTO)
                .collect(Collectors.toList());
    }


    public BpmnDiagramTO getSingleDiagram(String bpmnDiagramId){
        return this.mapper.toTO(this.bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId));
    }


    public void updateUpdatedDate(String bpmnDiagramId){
        BpmnDiagramEntity bpmnDiagramEntity = this.bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId);
        BpmnDiagram bpmnDiagram = this.mapper.toModel(bpmnDiagramEntity);
        bpmnDiagram.updateDate();
        log.debug("Updating Date");
        this.saveToDb(bpmnDiagram);
    }


    private void saveToDb(BpmnDiagram bpmnDiagram){
        bpmnDiagramJpa.save(this.mapper.toEntity(bpmnDiagram));

    }



    public void deleteDiagram(String bpmnDiagramId){
        int deletedDiagrams = this.bpmnDiagramJpa.deleteBpmnDiagramEntitiyByBpmnDiagramId(bpmnDiagramId);
        log.info(String.format("Deleted %s Diagram", deletedDiagrams));
    }


    public void deleteAllByRepositoryId(String bpmnRepositoryId){
        //Auth check performed in Facade
        int deletedDiagrams = this.bpmnDiagramJpa.deleteAllByBpmnRepositoryId(bpmnRepositoryId);
        log.debug(String.format("Deleted %s diagrams", deletedDiagrams));

    }
}
