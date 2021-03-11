package io.bpmnrepo.backend.diagram.api.resource;


import io.bpmnrepo.backend.diagram.BpmnDiagramFacade;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.shared.mapper.Mapper;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diagram")
public class BpmnDiagramController {

    private final BpmnDiagramFacade bpmnDiagramFacade;


    //create new Diagram, parent RepositoryID has to be passed
    @PostMapping()
    public ResponseEntity<Void> createDiagram(@RequestBody @Validated final BpmnDiagramTO bpmnDiagramTO){
        System.out.println("creating new Diagram: \"" + bpmnDiagramTO.getBpmnDiagramName() + "\"");
        this.bpmnDiagramFacade.createDiagram(bpmnDiagramTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all/{repositoryId}")
    public ResponseEntity<List<BpmnDiagramTO>> getDiagramsFromRepo(@PathVariable @NotBlank String repositoryId){
        //Exceptionhandling for n.a. repositoryId
        System.out.println(String.format("Returning diagrams from repository %s", repositoryId));
        return ResponseEntity.ok().body(this.bpmnDiagramFacade.getDiagramsFromRepo(repositoryId));

    }


    @GetMapping("/{bpmnRepositoryId}/{bpmnDiagramId}")
    public ResponseEntity<BpmnDiagramTO> getSingleDiagram(@PathVariable @NotBlank String bpmnRepositoryId,
                                                          @PathVariable @NotBlank String bpmnDiagramId){
        System.out.println("Returning diagram with id" + bpmnDiagramId);
        return ResponseEntity.ok().body(this.bpmnDiagramFacade.getSingleDiagram(bpmnRepositoryId, bpmnDiagramId));
    }

    @DeleteMapping("{bpmnRepositoryId}/{bpmnDiagramId}")
    @Operation(summary = "Delete one Diagram and all of its versions")
    public ResponseEntity<Void> deleteDiagram(@PathVariable @NotBlank String bpmnRepositoryId,
                                              @PathVariable @NotBlank String bpmnDiagramId){
        System.out.println("Deleting Diagram with ID " + bpmnDiagramId);
        this.bpmnDiagramFacade.deleteDiagram(bpmnRepositoryId, bpmnDiagramId);
        return ResponseEntity.ok().build();
    }
}
