package io.bpmnrepo.backend.repository.domain.business;


import io.bpmnrepo.backend.repository.domain.mapper.RepositoryMapper;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramEntity;
import io.bpmnrepo.backend.repository.domain.model.BpmnRepository;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.exception.AccessRightException;
import io.bpmnrepo.backend.repository.infrastructure.repository.AssignmentJpa;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramJpa;
import io.bpmnrepo.backend.repository.infrastructure.repository.BpmnRepoJpa;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.user.domain.business.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BpmnRepositoryService {


    private final RepositoryMapper mapper;
    private final UserService userService;
    private final BpmnDiagramService bpmnDiagramService;
    private final AssignmentService assignmentService;
    private final AuthService authService;
    private final BpmnRepoJpa bpmnRepoJpa;
    private final AssignmentJpa assignmentJpa;
    private final BpmnDiagramJpa bpmnDiagramJpa;




    public void createRepository(BpmnRepositoryTO bpmnRepositoryTO){
        BpmnRepository bpmnRepository = new BpmnRepository(bpmnRepositoryTO);
        BpmnRepositoryEntity bpmnRepositoryEntity = this.mapper.toEntity(bpmnRepository);
        this.saveToDb(bpmnRepositoryEntity);
        System.out.println(bpmnRepositoryEntity.getBpmnRepositoryId());
        this.assignmentService.createInitialAssignment(bpmnRepositoryEntity.getBpmnRepositoryId());
    }


    public List<BpmnRepositoryTO> getAllRepositories() {
        final String userId = this.userService.getUserIdOfCurrentUser();

        //1. start a query carrying userId to receive all AssignmentEntities related to the user
        //2. for all AssignmentEntities: get the RepositoryIds
        //3. for every RepositoryId: start a query to get the RepositoryEntities
        return this.assignmentJpa.findAssignmentEntitiesByAssignmentId_UserIdEquals(userId).stream()
                .map(element -> element.getAssignmentId().getBpmnRepositoryId())
                .map(this.bpmnRepoJpa::findByBpmnRepositoryId)
                .map(this.mapper::toTO)
                .collect(Collectors.toList());
    }


    public BpmnRepositoryTO getSingleRepository(String repositoryId){
        if(this.authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER)){
            return this.mapper.toTO(this.bpmnRepoJpa.findByBpmnRepositoryId(repositoryId));
        }
        else{
            return null;
        }
    }

    public void updateRepository(BpmnRepositoryTO bpmnRepositoryTO){
        if(bpmnRepositoryTO.getBpmnRepositoryId() == null){
            log.debug("No Repository ID provided");
        }
        else{
            if(this.authService.checkIfOperationIsAllowed(bpmnRepositoryTO.getBpmnRepositoryId(), RoleEnum.ADMIN)) {
                BpmnRepositoryEntity bpmnRepositoryEntity = this.bpmnRepoJpa.getOne(bpmnRepositoryTO.getBpmnRepositoryId());
                BpmnRepository bpmnRepository = new BpmnRepository(bpmnRepositoryTO);
                if(bpmnRepository.getBpmnRepositoryName() != null && !bpmnRepository.getBpmnRepositoryName().isEmpty()){
                    bpmnRepositoryEntity.setBpmnRepositoryName(bpmnRepositoryTO.getBpmnRepositoryName());
                }
                if(bpmnRepository.getBpmnRepositoryDescription() != null && !bpmnRepository.getBpmnRepositoryDescription().isEmpty()){
                    bpmnRepositoryEntity.setBpmnRepositoryDescription(bpmnRepository.getBpmnRepositoryDescription());
                }
                bpmnRepositoryEntity.setUpdatedDate(bpmnRepository.getUpdatedDate());
                this.saveToDb(bpmnRepositoryEntity);
            }
            else{
                throw new AccessRightException("Only Admins and Owners are allowed to change the repository parameters");
            }
        }
    }


    public void deleteRepository(String repositoryId){
        if(this.authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.OWNER)){
            List<BpmnDiagramEntity> bpmnDiagramEntityList = this.bpmnDiagramJpa.findBpmnDiagramEntitiesByBpmnDiagramRepository_BpmnRepositoryIdEquals(repositoryId);
            Integer diagramsCount = bpmnDiagramEntityList.size();      //Nur zum Debuggen

            //1. Delete diagrams and the corresponding versions - deleteDiagram method contains the cascade logic that also deletes the child-diagram-versions
            bpmnDiagramEntityList.forEach(bpmnDiagramEntity -> this.bpmnDiagramService.deleteDiagram(bpmnDiagramEntity.getBpmnDiagramId()));
            //2. Delete Repository
            this.bpmnRepoJpa.deleteBpmnRepositoryEntityByBpmnRepositoryId(repositoryId);
            //3. Delete all related assignments
            this.assignmentJpa.deleteAssignmentEntitiesByAssignmentId_BpmnRepositoryId(repositoryId);
            System.out.println("Deleted Repository including " + diagramsCount.toString() + " diagrams");
        }
        else{
            throw new AccessRightException("Only the owner is allowed to delete the repository");
        }
    }


    public void saveToDb(final BpmnRepositoryEntity bpmnRepositoryEntity){
        bpmnRepoJpa.save(bpmnRepositoryEntity);
    }

}
