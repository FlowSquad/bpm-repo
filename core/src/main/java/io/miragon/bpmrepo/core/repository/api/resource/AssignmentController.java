package io.miragon.bpmrepo.core.repository.api.resource;

import io.miragon.bpmrepo.core.repository.api.mapper.AssignmentApiMapper;
import io.miragon.bpmrepo.core.repository.api.transport.AssignmentTO;
import io.miragon.bpmrepo.core.repository.api.transport.AssignmentUpdateTO;
import io.miragon.bpmrepo.core.repository.domain.business.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final AssignmentApiMapper assignmentApiMapper;

    /**
     * Create or update user assignment
     *
     * @param assignmentUpdateTO Assignment update
     */
    @PostMapping
    @Operation(summary = "Create / update user assignment")
    public ResponseEntity<Void> createOrUpdateUserAssignment(@RequestBody @Valid final AssignmentUpdateTO assignmentUpdateTO) {
        log.debug("Creating new Assignment for " + assignmentUpdateTO.getUsername());
        this.assignmentService.createOrUpdateAssignment(this.assignmentApiMapper.mapUpdate(assignmentUpdateTO));
        return ResponseEntity.ok().build();
    }

    /**
     * Delete user assignment
     *
     * @param repositoryId Id of the repository
     * @param username     User that should be removed
     */
    @DeleteMapping("/{repositoryId}/{username}")
    @Operation(summary = "Delete user assignment")
    public ResponseEntity<Void> deleteUserAssignment(@PathVariable final String repositoryId, @PathVariable final String username) {
        log.debug(String.format("Deleting assignment for user %s", username));
        this.assignmentService.deleteAssignment(repositoryId, username);
        return ResponseEntity.ok().build();
    }

    /**
     * Return all user assignments for the given repository
     *
     * @param repositoryId Id of the repository
     * @return assignments
     */
    @GetMapping("/{repositoryId}")
    @Operation(summary = "Get all assigned users")
    public ResponseEntity<List<AssignmentTO>> getAllAssignedUsers(@PathVariable final String repositoryId) {
        log.debug(String.format("Returning all assigned Users for Repository %s", repositoryId));
        final List<io.miragon.bpmrepo.core.repository.domain.model.Assignment> assignedUsers = this.assignmentService.getAllAssignedUsers(repositoryId);
        return ResponseEntity.ok(this.assignmentApiMapper.mapToTO(assignedUsers));
    }

}
