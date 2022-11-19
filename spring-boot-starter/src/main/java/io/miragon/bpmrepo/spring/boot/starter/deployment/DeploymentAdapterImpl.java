package io.miragon.bpmrepo.spring.boot.starter.deployment;

import io.miragon.bpmrepo.core.artifact.adapter.DeploymentAdapter;
import io.miragon.bpmrepo.core.artifact.domain.service.DeploymentStatusService;
import org.springframework.stereotype.Component;

@Component
public class DeploymentAdapterImpl extends DeploymentAdapter {

    public DeploymentAdapterImpl(final DeploymentStatusService deploymentStatusService) {
        super(deploymentStatusService);
    }
}
