package io.miragon.bpmnrepo.spring.boot.starter.diagram;

import io.miragon.bpmrepo.core.diagram.domain.plugin.DeploymentPlugin;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultDeploymentPlugin implements DeploymentPlugin {

    @Override
    public void deploy(final String name, final String xml, final String target) {
        log.error("Deployment executed, but no Plugin available Name: {} target: {}", name, target);
    }
}
