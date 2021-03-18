package io.bpmnrepo.backend.repository.infrastructure.repository;

import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


//@Repository
public interface BpmnRepoJpa extends JpaRepository<BpmnRepositoryEntity, String>{
    List<BpmnRepositoryEntity> findAllByBpmnRepositoryNameIsNot(String bpmnRepositoryName);

    List<BpmnRepositoryEntity> findAllByBpmnRepositoryId(String bpmnRepositoryId);

    BpmnRepositoryEntity findByBpmnRepositoryIdAndBpmnRepositoryName(String bpmnRepositoryId, String bpmnRepositoryName);

    @Transactional
    void deleteBpmnRepositoryEntityByBpmnRepositoryId(String bpmnRepositoryId);

    BpmnRepositoryEntity findByBpmnRepositoryId(String bpmnRepositoryId);
    Optional<BpmnRepositoryEntity> findByBpmnRepositoryIdEquals(String bpmnRepositoryId);
    //Boolean findByBpmnRepositoryIdExists(String bpmnRepositoryId);
}
