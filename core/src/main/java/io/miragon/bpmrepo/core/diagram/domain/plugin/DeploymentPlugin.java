package io.miragon.bpmrepo.core.diagram.domain.plugin;

public interface DeploymentPlugin {

    void deploy(String name, String xml, String target);

}
