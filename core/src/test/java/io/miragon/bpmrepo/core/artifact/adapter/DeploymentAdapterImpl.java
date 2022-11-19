package io.miragon.bpmrepo.core.artifact.adapter;

import io.miragon.bpmrepo.core.artifact.domain.enums.DeploymentStatus;
import io.miragon.bpmrepo.core.artifact.domain.model.Deployment;
import io.miragon.bpmrepo.core.artifact.domain.service.DeploymentStatusService;
import org.springframework.stereotype.Component;

@Component
public class DeploymentAdapterImpl extends DeploymentAdapter {

    public DeploymentAdapterImpl(final DeploymentStatusService deploymentStatusService) {
        super(deploymentStatusService);
    }

    public Deployment successfulDeployment(final String deploymentId) {
        return this.updateDeployment(deploymentId, DeploymentStatus.SUCCESS, "Deployment was successful");
    }

    public Deployment failedDeployment(final String deploymentId) {
        return this.updateDeployment(deploymentId, DeploymentStatus.ERROR, "Deployment failed");
    }

}
