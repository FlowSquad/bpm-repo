package io.miragon.bpmrepo.spring.boot.starter;

import io.miragon.bpmrepo.core.artifact.domain.service.DeploymentStatusService;
import io.miragon.bpmrepo.core.artifact.plugin.ArtifactTypesPlugin;
import io.miragon.bpmrepo.core.artifact.plugin.DeploymentPlugin;
import io.miragon.bpmrepo.spring.boot.starter.deployment.DefaultDeploymentPlugin;
import io.miragon.bpmrepo.spring.boot.starter.deployment.DeploymentAdapterImpl;
import io.miragon.bpmrepo.spring.boot.starter.fileTypes.DefaultFileTypesPlugin;
import io.miragon.digiwf.digiwfdeploymentproxy.properties.DeploymentProxyProperties;
import io.miragon.digiwf.digiwfdeploymentproxy.service.DeploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@RequiredArgsConstructor
@Configuration
@EnableJpaRepositories(basePackages = "io.miragon.bpmrepo.core")
@EntityScan(basePackages = "io.miragon.bpmrepo.core")
@ComponentScan(basePackages = "io.miragon.bpmrepo.core")
public class BpmRepoAutoConfiguration {

    private final DeploymentService deploymentService;
    private final DeploymentProxyProperties deploymentProxyProperties;
    private final DeploymentStatusService deploymentStatusService;

    @Bean
    @ConditionalOnMissingBean
    public DeploymentPlugin deploymentPlugin() {
        return new DefaultDeploymentPlugin(this.deploymentService, new DeploymentAdapterImpl(this.deploymentStatusService), this.deploymentProxyProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ArtifactTypesPlugin fileTypesPlugin() {
        return new DefaultFileTypesPlugin();
    }

}
