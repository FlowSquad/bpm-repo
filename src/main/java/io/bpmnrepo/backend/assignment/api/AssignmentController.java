package io.bpmnrepo.backend.assignment.api;

import io.bpmnrepo.backend.shared.Mapper;
import io.bpmnrepo.backend.assignment.domain.AssignmentService;
import io.bpmnrepo.backend.user.domain.UserService;
import io.bpmnrepo.backend.assignment.api.AssignmentTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final Mapper mapper;
    private final AssignmentService assignmentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUserAssignment(@RequestBody @Validated AssignmentTO assignmentTO){
        System.out.println("Creating new Assignment for " + assignmentTO.getUserName());
        this.assignmentService.createAssignment(assignmentTO);
        System.out.println("Created Assignment");
        return ResponseEntity.ok().build();
    }
}
