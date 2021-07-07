package io.miragon.bpmrepo.core.diagram.domain.plugin;

import java.util.List;

public interface DeploymentPlugin {

    void deploy(String name, String xml, String target);

    List<String> getDeploymentTargets();

}
