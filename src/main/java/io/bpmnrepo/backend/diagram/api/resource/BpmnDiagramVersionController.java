package io.bpmnrepo.backend.diagram.api.resource;


import com.sun.istack.NotNull;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.shared.mapper.Mapper;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/version")
public class BpmnDiagramVersionController {

    private final BpmnDiagramVersionService bpmnDiagramVersionService;
    private final Mapper mapper;


    @PostMapping
    @Operation(summary = "Create a new Version of a Diagram")
    public ResponseEntity<Void> createNewVersion(@RequestBody @Validated final BpmnDiagramVersionTO bpmnDiagramVersionTO){
        System.out.println("Creating a new version");
        this.bpmnDiagramVersionService.createNewVersion(bpmnDiagramVersionTO);
        return ResponseEntity.ok().build();
    }

    //get the latest version of a diagram
    @GetMapping("{bpmnDiagramId}")
    @Operation(summary = "Return the latest version of the requested diagram")
    public ResponseEntity<BpmnDiagramVersionTO> getLatestVersion(@PathVariable @NotNull final String bpmnDiagramId){
        System.out.println("Returning latest version of Diagram " + bpmnDiagramId);
        return ResponseEntity.ok().body(this.bpmnDiagramVersionService.getLatestVersion(bpmnDiagramId));
    }

    //get all versions by providing the corresponding parent diagram id
    @GetMapping("/all/{bpmnDiagramId}")
    public ResponseEntity<List<BpmnDiagramVersionTO>> getAllVersions(@PathVariable @NotNull final String bpmnDiagramId){
        System.out.println("Returning all Versions of Diagram " + bpmnDiagramId);
        return ResponseEntity.ok().body(this.bpmnDiagramVersionService.getAllVersions(bpmnDiagramId));
    }

    //get one specific version by providing version id
    @GetMapping("/single/{bpmnDiagramVersionId}")
    public ResponseEntity<BpmnDiagramVersionTO> getSingleVersion(@PathVariable @NotNull final String bpmnDiagramVersionId){
        System.out.println("Returning single Version");
        return ResponseEntity.ok().body(this.bpmnDiagramVersionService.getSingleVersion(bpmnDiagramVersionId));
    }
}
