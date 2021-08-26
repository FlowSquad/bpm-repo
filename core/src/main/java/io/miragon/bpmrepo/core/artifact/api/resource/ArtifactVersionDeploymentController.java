package io.miragon.bpmrepo.core.artifact.api.resource;

import io.miragon.bpmrepo.core.artifact.api.mapper.ArtifactVersionApiMapper;
import io.miragon.bpmrepo.core.artifact.api.mapper.DeploymentApiMapper;
import io.miragon.bpmrepo.core.artifact.api.transport.ArtifactVersionTO;
import io.miragon.bpmrepo.core.artifact.api.transport.NewDeploymentTO;
import io.miragon.bpmrepo.core.artifact.domain.model.ArtifactVersion;
import io.miragon.bpmrepo.core.artifact.domain.service.ArtifactVersionDeploymentService;
import io.miragon.bpmrepo.core.user.domain.model.User;
import io.miragon.bpmrepo.core.user.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Validated
@Transactional
@RestController
@RequiredArgsConstructor
@Tag(name = "Deployment")
@RequestMapping("/api/deploy")
public class ArtifactVersionDeploymentController {

    private final ArtifactVersionDeploymentService deploymentService;
    private final UserService userService;
    private final ArtifactVersionApiMapper versionApiMapper;
    private final DeploymentApiMapper deploymentApiMapper;

    /**
     * Deploy a specific version of an artifact
     *
     * @param deployment deployment object containing ids and target
     * @return the artifactversion containing the list of deployments
     */
    @Operation(summary = "Deploy artifact version")
    @PostMapping()
    public ResponseEntity<ArtifactVersionTO> deployVersion(@RequestBody final NewDeploymentTO deployment) {
        log.debug("Deploying artifact version {}", deployment.getVersionId());
        final User user = this.userService.getCurrentUser();
        final ArtifactVersion artifactVersion = this.deploymentService.deploy(deployment.getArtifactId(), deployment.getVersionId(), deployment.getTarget(), user);
        return ResponseEntity.ok().body(this.versionApiMapper.mapToTO(artifactVersion));
    }

    /**
     * Deploy versions of multiple artifacts
     *
     * @param deployments list of deployment objects containing ids and target
     * @return the list of artifactversions containing their new deployments
     */
    @Operation(summary = "Deploy multiple versions")
    @PostMapping("/list")
    public ResponseEntity<List<ArtifactVersionTO>> deployMultipleVersions(@RequestBody final List<NewDeploymentTO> deployments) {
        log.debug("Deploying {} artifact versions", deployments.size());
        final User user = this.userService.getCurrentUser();
        final List<ArtifactVersion> artifactVersions = this.deploymentService.deployMultiple(this.deploymentApiMapper.mapToModel(deployments), user);
        return ResponseEntity.ok().body(this.versionApiMapper.mapToTO(artifactVersions));
    }

    /**
     * Get all available deployment targets
     *
     * @return available deployment targets as list of strings
     */
    @Operation(summary = "Get all available deployment targets")
    @GetMapping("/target")
    public ResponseEntity<List<String>> getAllDeploymentTargets() {
        log.debug("Returning all deployment targets");
        final List<String> deploymentTargets = this.deploymentService.getDeploymentTargets();
        return ResponseEntity.ok(deploymentTargets);
    }

}
