package io.bpmnrepo.backend.repository.domain.business;


import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryRequestTO;
import io.bpmnrepo.backend.repository.api.transport.NewBpmnRepositoryTO;
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

    public String createRepository(NewBpmnRepositoryTO newBpmnRepositoryTO){
        BpmnRepository bpmnRepository = this.mapper.toModel(newBpmnRepositoryTO);
        BpmnRepositoryEntity bpmnRepositoryEntity = this.mapper.toEntity(bpmnRepository);
        bpmnRepository = this.saveToDb(bpmnRepositoryEntity);
        return bpmnRepository.getBpmnRepositoryId();
    }



    public void updateRepository(String bpmnRepositoryId, NewBpmnRepositoryTO newBpmnRepositoryTO){
        BpmnRepositoryEntity bpmnRepositoryEntity = this.bpmnRepoJpa.getOne(bpmnRepositoryId);
        BpmnRepository bpmnRepository = this.mapper.toModel(bpmnRepositoryEntity);
        bpmnRepository.updateRepo(newBpmnRepositoryTO);
        bpmnRepositoryEntity = this.mapper.toEntity(bpmnRepository);
        this.saveToDb(bpmnRepositoryEntity);
    }

    public BpmnRepositoryRequestTO getSingleRepository(String repositoryId){
        return this.mapper.toRequestTO(this.bpmnRepoJpa.findByBpmnRepositoryId(repositoryId));
    }

    public void updateAssignedUsers(String bpmnRepositoryId, Integer assignedUsers){
        BpmnRepository bpmnRepository = this.mapper.toModel(this.bpmnRepoJpa.findByBpmnRepositoryId(bpmnRepositoryId));
        bpmnRepository.setAssignedUsers(assignedUsers);
        this.bpmnRepoJpa.save(this.mapper.toEntity(bpmnRepository));
    }

    public void updateExistingDiagrams(String bpmnRepositoryId, Integer existingDiagrams){
        BpmnRepository bpmnRepository = this.mapper.toModel(this.bpmnRepoJpa.findByBpmnRepositoryId(bpmnRepositoryId));
        bpmnRepository.setExistingDiagrams(existingDiagrams);
        this.bpmnRepoJpa.save(this.mapper.toEntity(bpmnRepository));
    }

    public void deleteRepository(String bpmnRepositoryId){
        this.bpmnRepoJpa.deleteBpmnRepositoryEntityByBpmnRepositoryId(bpmnRepositoryId);
    }


    public BpmnRepository saveToDb(final BpmnRepositoryEntity bpmnRepositoryEntity){
        return this.mapper.toModel(bpmnRepoJpa.save(bpmnRepositoryEntity));
    }
}
