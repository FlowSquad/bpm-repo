package io.bpmnrepo.backend.diagram.api.resource;


import com.sun.istack.NotNull;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.domain.mapper.VersionMapper;
import io.bpmnrepo.backend.shared.mapper.Mapper;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/version")
public class BpmnDiagramVersionController {

    private final BpmnDiagramVersionService bpmnDiagramVersionService;

    @PostMapping
    @Operation(summary = "Create new Version of a Diagram")
    public ResponseEntity<Void> createOrUpdateVersion(@RequestBody @Validated final BpmnDiagramVersionTO bpmnDiagramVersionTO){
        System.out.println("Creating a new version");
        this.bpmnDiagramVersionService.createOrUpdateVersion(bpmnDiagramVersionTO);
        return ResponseEntity.ok().build();
    }

    //get the latest version of a diagram
    @GetMapping("/{bpmnRepositoryId}/{bpmnDiagramId}")
    @Operation(summary = "Return the latest version of the requested diagram")
    public ResponseEntity<BpmnDiagramVersionTO> getLatestVersion(@PathVariable @NotBlank final String bpmnRepositoryId,
                                                                 @PathVariable @NotBlank final String bpmnDiagramId){
        System.out.println(bpmnRepositoryId);
        System.out.println("Returning latest version of Diagram " + bpmnDiagramId);
        return ResponseEntity.ok().body(this.bpmnDiagramVersionService.getLatestVersion(bpmnRepositoryId, bpmnDiagramId));
    }

    //get all versions by providing the corresponding parent diagram id
    @GetMapping("/all/{bpmnRepositoryId}/{bpmnDiagramId}")
    public ResponseEntity<List<BpmnDiagramVersionTO>> getAllVersions(@PathVariable @NotBlank final String bpmnRepositoryId,
                                                                     @PathVariable @NotBlank final String bpmnDiagramId){
        System.out.println("Returning all Versions of Diagram " + bpmnDiagramId);
        return ResponseEntity.ok().body(this.bpmnDiagramVersionService.getAllVersions(bpmnRepositoryId, bpmnDiagramId));
    }

    //get one specific version by providing version id
    //diagramID act. not neccessary
    @GetMapping("/{bpmnRepositoryId}/{bpmnDiagramId}/{bpmnDiagramVersionId}")
    public ResponseEntity<BpmnDiagramVersionTO> getSingleVersion(@PathVariable @NotBlank final String bpmnRepositoryId,
                                                                 @PathVariable @NotBlank final String bpmnDiagramId,
                                                                 @PathVariable @NotBlank final String bpmnDiagramVersionId){
        System.out.println("Returning single Version");
        return ResponseEntity.ok().body(this.bpmnDiagramVersionService.getSingleVersion(bpmnRepositoryId, bpmnDiagramId, bpmnDiagramVersionId));
    }
}
