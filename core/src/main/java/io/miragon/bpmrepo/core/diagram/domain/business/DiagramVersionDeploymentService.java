package io.miragon.bpmrepo.core.diagram.domain.business;

import io.miragon.bpmrepo.core.diagram.domain.model.Diagram;
import io.miragon.bpmrepo.core.diagram.domain.model.DiagramVersion;
import io.miragon.bpmrepo.core.diagram.domain.plugin.DeploymentPlugin;
import io.miragon.bpmrepo.core.repository.domain.business.AuthService;
import io.miragon.bpmrepo.core.shared.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiagramVersionDeploymentService {

    private final DiagramVersionService diagramVersionService;
    private final DiagramService diagramService;
    private final AuthService authService;

    private final DeploymentPlugin deploymentPlugin;

    public void deploy(final String diagramId, final String versionId, final String target, final String user) {
        log.debug("Deploy diagram version {} on target {} by user {}", versionId, target, user);
        final Diagram diagram = this.diagramService.getDiagramById(diagramId);
        this.authService.checkIfOperationIsAllowed(diagram.getRepositoryId(), RoleEnum.ADMIN);
        final DiagramVersion version = this.diagramVersionService.getVersion(versionId);
        this.deploymentPlugin.deploy(diagram.getName(), version.getXml(), target);
        version.deploy(target, user);
        this.diagramVersionService.saveToDb(version);
    }

    public List<String> getDeploymentTargets() {
        return this.deploymentPlugin.getDeploymentTargets();
    }

}
