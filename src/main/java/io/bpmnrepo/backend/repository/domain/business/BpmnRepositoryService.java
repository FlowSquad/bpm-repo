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
    private final BpmnRepoJpa bpmnRepoJpa;

    public String createRepository(BpmnRepositoryTO bpmnRepositoryTO){
        BpmnRepository bpmnRepository = new BpmnRepository(bpmnRepositoryTO);
        BpmnRepositoryEntity bpmnRepositoryEntity = this.mapper.toEntity(bpmnRepository);
        this.saveToDb(bpmnRepositoryEntity);
        return bpmnRepositoryEntity.getBpmnRepositoryId();
    }


    public void updateRepository(BpmnRepositoryTO bpmnRepositoryTO){
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

    public BpmnRepositoryTO getSingleRepository(String repositoryId){
        return this.mapper.toTO(this.bpmnRepoJpa.findByBpmnRepositoryId(repositoryId));
    }

    public void deleteRepository(String bpmnRepositoryId){
        this.bpmnRepoJpa.deleteBpmnRepositoryEntityByBpmnRepositoryId(bpmnRepositoryId);
    }


    public void saveToDb(final BpmnRepositoryEntity bpmnRepositoryEntity){
        bpmnRepoJpa.save(bpmnRepositoryEntity);
    }

}
