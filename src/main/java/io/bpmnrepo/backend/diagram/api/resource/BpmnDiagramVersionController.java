package io.bpmnrepo.backend.diagram.api.resource;


import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.BpmnDiagramVersionFacade;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionUploadTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/version")
public class BpmnDiagramVersionController {

    private final BpmnDiagramVersionFacade bpmnDiagramVersionFacade;


    @PostMapping("/{bpmnRepositoryId}/{bpmnDiagramId}")
    public ResponseEntity<Void> createInitialVersion(@PathVariable @NotBlank String bpmnRepositoryId,
                                              @PathVariable @NotBlank String bpmnDiagramId,
                                              @RequestBody BpmnDiagramVersionUploadTO bpmnDiagramVersionUploadTO){
        bpmnDiagramVersionFacade.createInitialVersion(bpmnRepositoryId, bpmnDiagramId, bpmnDiagramVersionUploadTO);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/update/{bpmnRepositoryId}/{bpmnDiagramId}")
    public ResponseEntity<Void> updateVersion(@PathVariable @NotBlank String bpmnRepositoryId,
                                        @PathVariable @NotBlank String bpmnDiagramId,
                                        @RequestBody BpmnDiagramVersionTO bpmnDiagramVersionTO) {
        System.out.println("Creating a new version - body accepted");
        System.out.println(bpmnDiagramVersionTO.getClass());
        System.out.println(bpmnDiagramVersionTO.getBpmnAsXML());
        this.bpmnDiagramVersionFacade.updateVersion(bpmnRepositoryId, bpmnDiagramId, bpmnDiagramVersionTO);
        return ResponseEntity.ok().build();
    }

    //get the latest version of a diagram
    @GetMapping("/{bpmnRepositoryId}/{bpmnDiagramId}")
    @Operation(summary = "Return the latest version of the requested diagram")
    public ResponseEntity<BpmnDiagramVersionTO> getLatestVersion(@PathVariable @NotBlank final String bpmnRepositoryId,
                                                                 @PathVariable @NotBlank final String bpmnDiagramId){
        System.out.println("Returning latest version");
        return ResponseEntity.ok().body(this.bpmnDiagramVersionFacade.getLatestVersion(bpmnRepositoryId, bpmnDiagramId));
    }

    //get all versions by providing the corresponding parent diagram id
    @GetMapping("/all/{bpmnRepositoryId}/{bpmnDiagramId}")
    public ResponseEntity<List<BpmnDiagramVersionTO>> getAllVersions(@PathVariable @NotBlank final String bpmnRepositoryId,
                                                                     @PathVariable @NotBlank final String bpmnDiagramId){
        System.out.println("Returning all Versions");
        return ResponseEntity.ok().body(this.bpmnDiagramVersionFacade.getAllVersions(bpmnRepositoryId, bpmnDiagramId));
    }

    //get one specific version by providing version id
    //diagramID act. not neccessary
    @GetMapping("/{bpmnRepositoryId}/{bpmnDiagramId}/{bpmnDiagramVersionId}")
    public ResponseEntity<BpmnDiagramVersionTO> getSingleVersion(@PathVariable @NotBlank final String bpmnRepositoryId,
                                                                 @PathVariable @NotBlank final String bpmnDiagramId,
                                                                 @PathVariable @NotBlank final String bpmnDiagramVersionId){
        System.out.println("Returning single Version");
        return ResponseEntity.ok().body(this.bpmnDiagramVersionFacade.getSingleVersion(bpmnRepositoryId, bpmnDiagramId, bpmnDiagramVersionId));
    }
}
