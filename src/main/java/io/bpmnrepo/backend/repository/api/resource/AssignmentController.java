package io.bpmnrepo.backend.repository.api.resource;

import io.bpmnrepo.backend.repository.api.transport.AssignmentDeletionTO;
import io.bpmnrepo.backend.repository.api.transport.AssignmentWithUserNameTO;
import io.bpmnrepo.backend.repository.api.transport.AssignmentTO;
import io.bpmnrepo.backend.repository.domain.business.AssignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

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

    /** User komplett vom Repository entfernen. Kann von Admind und Ownern ausgeführt werden
     *
     * @param assignmentDeletionTO
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUserAssignment(@RequestBody @Valid AssignmentDeletionTO assignmentDeletionTO){
        log.debug(String.format("Deleting assignment for user %s", assignmentDeletionTO.getUserName()));
        this.assignmentService.deleteAssignment(assignmentDeletionTO);
        return ResponseEntity.ok().build();
    }
}
