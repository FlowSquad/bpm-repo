package io.miragon.bpmrepo.core.diagram.api.resource;

import io.miragon.bpmrepo.core.diagram.api.transport.DeplyomentTO;
import io.miragon.bpmrepo.core.diagram.domain.business.DiagramVersionDeploymentService;
import io.miragon.bpmrepo.core.security.UserContext;
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
public class DiagramVersionDeploymentController {

    private final DiagramVersionDeploymentService deploymentService;
    private final UserContext userContext;

    @Operation(description = "Deploy diagram version")
    @PostMapping("/{diagramId}/{versionId}")
    public ResponseEntity<Void> deployVersion(
            @PathVariable final String diagramId,
            @PathVariable final String versionId,
            @RequestBody final DeplyomentTO deplyoment) {
        this.deploymentService.deploy(diagramId, versionId, deplyoment.getTarget(), this.userContext.getUserName());
        return ResponseEntity.ok().build();

    }

    @Operation(description = "Get all available deployment targets")
    @GetMapping("/target")
    public ResponseEntity<List<String>> getAllDeploymentTargets() {
        final List<String> deploymentTargets = this.deploymentService.getDeploymentTargets();
        return ResponseEntity.ok(deploymentTargets);
    }

}
