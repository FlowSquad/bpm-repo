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
    private final AuthService authService;
    private final BpmnRepoJpa bpmnRepoJpa;





    public String createRepository(BpmnRepositoryTO bpmnRepositoryTO){
        BpmnRepository bpmnRepository = new BpmnRepository(bpmnRepositoryTO);
        BpmnRepositoryEntity bpmnRepositoryEntity = this.mapper.toEntity(bpmnRepository);
        System.out.println(bpmnRepositoryEntity.getBpmnRepositoryId() + "id");
        System.out.println(bpmnRepositoryEntity.getBpmnRepositoryDescription() + "desc");
        System.out.println(bpmnRepositoryEntity.getCreatedDate() + "date");

        this.saveToDb(bpmnRepositoryEntity);
        System.out.println(bpmnRepositoryEntity.getBpmnRepositoryId());
        return bpmnRepositoryEntity.getBpmnRepositoryId();
    }


/*
    public List<BpmnRepositoryTO> getAllRepositories(List<String> bpmnRepositoryIds) {
        return this.bpmnRepoJpa.findAllByBpmnRepositoryId(bpmnRepositoryIds).stream()
                    .map(this.mapper::toTO)
                    .collect(Collectors.toList());
    }*/


    public BpmnRepositoryTO getSingleRepository(String repositoryId){
        if(this.authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER)){
            return this.mapper.toTO(this.bpmnRepoJpa.findByBpmnRepositoryId(repositoryId));
        }
        else{
            throw new AccessRightException("You are not allowed to view this repository");
        }
    }


    public void updateRepository(BpmnRepositoryTO bpmnRepositoryTO){
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



    public void deleteRepository(String bpmnRepositoryId){
        this.bpmnRepoJpa.deleteBpmnRepositoryEntityByBpmnRepositoryId(bpmnRepositoryId);
    }


    public void saveToDb(final BpmnRepositoryEntity bpmnRepositoryEntity){
        bpmnRepoJpa.save(bpmnRepositoryEntity);
    }

}
