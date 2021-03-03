package io.bpmnrepo.backend.diagram.domain;

import io.bpmnrepo.backend.assignment.domain.AssignmentMapper;
import io.bpmnrepo.backend.shared.AccessRightException;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.diagram.infrastructure.BpmnDiagramEntity;
import io.bpmnrepo.backend.shared.RoleEnum;
import io.bpmnrepo.backend.diagram.infrastructure.BpmnDiagramJpa;
import io.bpmnrepo.backend.version.infrastructure.BpmnDiagramVersionJpa;
import io.bpmnrepo.backend.repository.infrastructure.BpmnRepoJpa;
import io.bpmnrepo.backend.repository.infrastructure.BpmnRepositoryEntity;
import io.bpmnrepo.backend.diagram.api.BpmnDiagramTO;
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
        if(this.authService.checkIfOperationIsAllowed(bpmnDiagramTO.getBpmnDiagramRepositoryId(), RoleEnum.MEMBER)) {
            BpmnDiagram bpmnDiagram = new BpmnDiagram(bpmnDiagramTO);
            saveToDb(this.mapper.toEntity(bpmnDiagram, getBpmnRepositoryById(bpmnDiagramTO.getBpmnDiagramRepositoryId())));
        }
        else{
            throw new AccessRightException("Only Members, Admins and Owners are allowed to create diagrams in this repository");
        }
    }


    public List<BpmnDiagramTO> getDiagramsFromRepo(String repositoryId){
        if(this.authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER)) {
            return this.bpmnDiagramJpa.findBpmnDiagramEntitiesByBpmnDiagramRepository_BpmnRepositoryIdEquals(repositoryId).stream()
                    .map(this.mapper::toTO)
                    .collect(Collectors.toList());
        }
        else{ return null; }
    }


    public BpmnDiagramTO getSingleDiagram(String bpmnDiagramId){
        if(this.authService.checkIfOperationIsAllowed(getRepositoryIdByDiagramId(bpmnDiagramId), RoleEnum.VIEWER)){
            return this.mapper.toTO(this.bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId));
        }
        else{
            return null;
        }
    }


    public void deleteDiagram(String bpmnDiagramId){
        System.out.println("Deleting diagram " + bpmnDiagramId);
        if(this.authService.checkIfOperationIsAllowed(getRepositoryIdByDiagramId(bpmnDiagramId), RoleEnum.OWNER)) {
            Integer diagramVersionsAnzahl = this.bpmnDiagramVersionJpa.findAllByBpmnDiagramEntity_BpmnDiagramId(bpmnDiagramId).size(); //just for debugging
            this.bpmnDiagramVersionJpa.deleteBpmnDiagramVersionEntitiesByBpmnDiagramEntity_BpmnDiagramId(bpmnDiagramId);
            this.bpmnDiagramJpa.deleteBpmnDiagramEntitiyByBpmnDiagramId(bpmnDiagramId);
            System.out.println("Deleted Diagram including " + diagramVersionsAnzahl.toString() + " child versions");
        }
        else{
            System.out.println("Deleting Diagram failed");
        }

    }

    public void saveToDb(BpmnDiagramEntity bpmnDiagramEntity){
        //check if user is allowed to
        if(authService.checkIfOperationIsAllowed(bpmnDiagramEntity.getBpmnDiagramRepository().getBpmnRepositoryId(), RoleEnum.MEMBER)){
            bpmnDiagramJpa.save(bpmnDiagramEntity);
            System.out.println("Created Diagram");
        }
        else{
            System.out.println("Creating Diagram failed");
        }
    }


    //___________________________ Helper ______________________

    public String getRepositoryIdByDiagramId(String bpmnDiagramId){
        return this.bpmnDiagramJpa.findBpmnDiagramEntityByBpmnDiagramIdEquals(bpmnDiagramId).getBpmnDiagramRepository().getBpmnRepositoryId();
    }

    public BpmnRepositoryEntity getBpmnRepositoryById(String repositoryId){
        if(this.authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER)){
            return bpmnRepoJpa.findByBpmnRepositoryId(repositoryId);
        }
        else{ return null; }
    }
}
