package io.bpmnrepo.backend.repository.api.resource;

import io.bpmnrepo.backend.repository.api.transport.AssignmentDeletionTO;
import io.bpmnrepo.backend.repository.api.transport.AssignmentWithUserNameTO;
import io.bpmnrepo.backend.repository.api.transport.AssignmentTO;
import io.bpmnrepo.backend.repository.domain.business.AssignmentService;
import io.bpmnrepo.backend.user.api.transport.UserInfoTO;
import io.bpmnrepo.backend.user.domain.business.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    private final UserService userService;

    /** Neue Assignments erstellen oder Rollen ändern. Kann von Admins und Ownern ausgeführt werden
     *
     * @param assignmentWithUserNameTO
     * @return
     */

    @PostMapping
    public ResponseEntity<Void> createOrUpdateUserAssignment(@RequestBody @Valid AssignmentWithUserNameTO assignmentWithUserNameTO){
        log.debug("Creating new Assignment for " + assignmentWithUserNameTO.getUserName());
        this.assignmentService.createOrUpdateAssignment(assignmentWithUserNameTO);
        return ResponseEntity.ok().build();
    }

    /** User komplett vom Repository entfernen. Kann von Admins und Ownern ausgeführt werden
     *
     * @param
     * @return
     */
    @DeleteMapping("/{repositoryId}/{userName}")
    public ResponseEntity<Void> deleteUserAssignment(@PathVariable String repositoryId, @PathVariable String userName){
        log.debug(String.format("Deleting assignment for user %s", userName));
        this.assignmentService.deleteAssignment(repositoryId, userName);
        return ResponseEntity.ok().build();
    }


    /** Alle user anfragen, die dem Repository zugeordnet sind
     *
     * @param
     * @return
     */
    @GetMapping("/{repositoryId}")
    public ResponseEntity<List<AssignmentTO>> getAllAssignedUsers(@PathVariable String repositoryId){
        log.debug(String.format("Returning all assigned Users for Repository %s", repositoryId));
        List<AssignmentTO> assignedUsers = this.assignmentService.getAllAssignedUsers(repositoryId);
        return ResponseEntity.ok().body(assignedUsers);
    }

}
