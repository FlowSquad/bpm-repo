package io.miragon.bpmrepo.core.artifact.domain.service;

import io.miragon.bpmrepo.core.artifact.domain.enums.DeploymentStatus;
import io.miragon.bpmrepo.core.artifact.domain.mapper.DeploymentMapper;
import io.miragon.bpmrepo.core.artifact.domain.model.Deployment;
import io.miragon.bpmrepo.core.artifact.infrastructure.repository.DeploymentJpaRepository;
import io.miragon.bpmrepo.core.shared.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeploymentStatusService {

    private final DeploymentJpaRepository deploymentJpaRepository;
    private final DeploymentMapper mapper;

    /**
     * Update a deployments status
     *
     * @param deploymentId
     * @param status
     * @param message
     * @return Deployment
     */
    public Deployment updateDeploymentStatus(final String deploymentId, final DeploymentStatus status, final String message) {
        final Deployment deployment = this.deploymentJpaRepository.findById(deploymentId)
                .map(this.mapper::toModel)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Deployment with id %s not found!", deploymentId)));
        deployment.update(status, message);
        return this.mapper.toModel(this.deploymentJpaRepository.save(this.mapper.toEntity(deployment)));
    }

}
