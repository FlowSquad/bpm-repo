package io.bpmnrepo.backend.diagram.domain.business;

import io.bpmnrepo.backend.diagram.domain.mapper.DiagramMapper;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagram;
import io.bpmnrepo.backend.shared.exception.AccessRightException;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramJpa;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramVersionJpa;
import io.bpmnrepo.backend.repository.infrastructure.repository.BpmnRepoJpa;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
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
    private final BpmnRepoJpa bpmnRepoJpa;
    private final BpmnDiagramVersionJpa bpmnDiagramVersionJpa;
    private final DiagramMapper mapper;
    private final AuthService authService;


    public void createDiagram(BpmnDiagramTO bpmnDiagramTO){
        if(this.authService.checkIfOperationIsAllowed(bpmnDiagramTO.getBpmnRepositoryId(), RoleEnum.MEMBER)) {
            BpmnDiagram bpmnDiagram = new BpmnDiagram(bpmnDiagramTO);
            saveToDb(this.mapper.toEntity(bpmnDiagram));
        }
        else{
            throw new AccessRightException("Only Members, Admins and Owners are allowed to create diagrams in this repository");
        }
    }


    public List<BpmnDiagramTO> getDiagramsFromRepo(String repositoryId){
        if(this.authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER)) {
            return this.bpmnDiagramJpa.findBpmnDiagramEntitiesByBpmnRepositoryId(repositoryId).stream()
                    .map(this.mapper::toTO)
                    .collect(Collectors.toList());
        }
        else{
            throw new AccessRightException("You are not allowed to view diagrams from this repository");
        }
    }


    public BpmnDiagramTO getSingleDiagram(String bpmnRepositoryId, String bpmnDiagramId){
        if(this.authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.VIEWER)){
            return this.mapper.toTO(this.bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId));
        }
        else{
            throw new AccessRightException("You are not allowed to delete this diagram");
        }
    }


    public void deleteDiagram(String bpmnRepositoryId, String bpmnDiagramId){
        System.out.println("Deleting diagram " + bpmnDiagramId);
        if(this.authService.checkIfOperationIsAllowed(bpmnRepositoryId, RoleEnum.ADMIN)) {
            int deletedDiagrams = this.bpmnDiagramJpa.deleteBpmnDiagramEntitiyByBpmnDiagramId(bpmnDiagramId);
            log.info(String.format("Deleted %s Diagram ?including child versions?", deletedDiagrams));
        }
        else{
            log.debug("Deleting Diagram failed");
        }
    }

    private void saveToDb(BpmnDiagramEntity bpmnDiagramEntity){
        if(authService.checkIfOperationIsAllowed(bpmnDiagramEntity.getBpmnRepositoryId(), RoleEnum.MEMBER)){
            bpmnDiagramJpa.save(bpmnDiagramEntity);
            log.debug("Created Diagram");
        }
        else{
            log.debug("Creating Diagram failed");
        }
    }


    public void deleteAllByRepositoryId(String bpmnRepositoryId){
            //Auth check performed in Facade
            int deletedDiagrams = this.bpmnDiagramJpa.deleteAllByBpmnRepositoryId(bpmnRepositoryId);
            log.debug(String.format("Deleted %s diagrams", deletedDiagrams));

    }
}
