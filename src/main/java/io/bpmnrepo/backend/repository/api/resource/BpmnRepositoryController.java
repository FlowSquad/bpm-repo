package io.bpmnrepo.backend.repository.api.resource;


import io.bpmnrepo.backend.repository.BpmnRepositoryFacade;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.business.BpmnRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bpmnrepo")
public class BpmnRepositoryController {

    private final BpmnRepositoryService bpmnRepositoryService;
    private final BpmnRepositoryFacade bpmnRepositoryFacade;
    //create new Repo
    @PostMapping()
    @Operation(summary = "Create a new Repository")
    public ResponseEntity<Void> createOrUpdateRepository(@RequestBody @Valid final BpmnRepositoryTO repositoryTO){
        bpmnRepositoryFacade.createOrUpdateRepository(repositoryTO);
        return ResponseEntity.ok().build();
    }

//Add the role to the returned element
    @GetMapping()
    @Operation(summary = "Get all Repositories")
    public ResponseEntity<List<BpmnRepositoryTO>> getAllRepositories(){
        //Checking for assigned Repos -> no Role checking required (you'll only receive Repositories you are assigned to)
        System.out.println("Returning all Repositories assigned to current user (NO_SECURITY_USER)");
        return ResponseEntity.ok().body(this.bpmnRepositoryFacade.getAllRepositories());
    }

    @GetMapping("/{repositoryId}")
    @Operation(summary = "Get a single Repository by providing its ID")
    public ResponseEntity<BpmnRepositoryTO> getSingleRepository(@PathVariable @NotBlank final String repositoryId){
        System.out.println(String.format("Returning single repository with id %s", repositoryId));
        return ResponseEntity.ok().body(this.bpmnRepositoryService.getSingleRepository(repositoryId));
    }

    //? USE THE SAME ENDPOINT FOR UPDATING AND CREATING A REPOSITORY?

    @DeleteMapping("/{repositoryId}")
    @Operation(summary = "Delete a Repository if you own it")
    public ResponseEntity<Void> deleteRepository(@PathVariable("repositoryId") @NotBlank final String repositoryId){
        System.out.println("Deleting Repository with ID " + repositoryId);
        this.bpmnRepositoryFacade.deleteRepository(repositoryId);

        return ResponseEntity.ok().build();
    }


    //update a Repository
    @PutMapping()
    public ResponseEntity<Void> updateRepository(@RequestBody @Validated final BpmnRepositoryTO bpmnRepositoryTO){
        System.out.println("Updating Repository");
        this.bpmnRepositoryService.updateRepository(bpmnRepositoryTO);
        return ResponseEntity.ok().build();
    }



}
