package io.miragon.bpmrepo.spring.boot.starter.deployment;


import io.miragon.bpmrepo.core.artifact.adapter.DeploymentAdapter;
import io.miragon.bpmrepo.core.artifact.domain.enums.DeploymentStatus;
import io.miragon.bpmrepo.core.artifact.plugin.DeploymentPlugin;
import io.miragon.digiwf.digiwfdeploymentproxy.dto.ArtifactDto;
import io.miragon.digiwf.digiwfdeploymentproxy.dto.DeploymentDto;
import io.miragon.digiwf.digiwfdeploymentproxy.dto.DeploymentSuccessDto;
import io.miragon.digiwf.digiwfdeploymentproxy.dto.FileDto;
import io.miragon.digiwf.digiwfdeploymentproxy.properties.DeploymentProxyProperties;
import io.miragon.digiwf.digiwfdeploymentproxy.service.DeploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class DefaultDeploymentPlugin implements DeploymentPlugin {

    private final DeploymentService deploymentService;
    private final DeploymentAdapter deploymentAdapter;
    private final DeploymentProxyProperties deploymentProxyProperties;

    @Override
    public void deploy(final String deploymentId, final String versionId, final String target, final String file, final String artifactType) {
        log.info("Deployed version {} to target {}", versionId, target);

        try {
            // name and project does not matter so far
            final DeploymentSuccessDto deploymentSuccess = this.deploymentService
                    .deploy(this.createDeploymentDto(deploymentId + ":" + versionId, deploymentId, target, file, artifactType.toLowerCase()));
            log.info("Deployment of artifact {} to target {} {}",
                    deploymentSuccess.getDeployment().getArtifact().getArtifactName(),
                    deploymentSuccess.getDeployment().getTarget(),
                    deploymentSuccess.isSuccess() ? "was successful" : "failed"
            );
            this.deploymentAdapter.updateDeployment(
                    deploymentId, deploymentSuccess.isSuccess() ? DeploymentStatus.SUCCESS : DeploymentStatus.ERROR,
                    deploymentSuccess.getMessage());
        } catch (final RuntimeException runtimeException) {
            log.error("Deployment failed with error", runtimeException);
            this.deploymentAdapter.updateDeployment(
                    deploymentId, DeploymentStatus.ERROR, runtimeException.getMessage());
        }
    }

    @Override
    public List<String> getDeploymentTargets() {
        // deploymentHandler keys are all available targets
        return new ArrayList<>(this.deploymentProxyProperties.getDeploymentHandlers().keySet());
    }

    private DeploymentDto createDeploymentDto(final String name, final String project, final String target, final String fileContent, final String type) {
        final FileDto file = FileDto.builder()
                .name(name)
                .content(fileContent)
                .extension(type)
                .size(fileContent.length())
                .build();
        final ArtifactDto artifact = ArtifactDto.builder()
                .type(type)
                .project(project)
                .file(file)
                .build();
        return DeploymentDto.builder()
                .target(target)
                .artifact(artifact)
                .build();
    }
}
