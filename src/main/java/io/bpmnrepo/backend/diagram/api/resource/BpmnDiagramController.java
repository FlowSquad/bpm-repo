package io.bpmnrepo.backend.diagram.api.resource;


import io.bpmnrepo.backend.diagram.BpmnDiagramFacade;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramUploadTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Slf4j
@RestController
@Transactional
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/diagram")
public class BpmnDiagramController {

    private final BpmnDiagramFacade bpmnDiagramFacade;

    /** Neue Repos erstellen oder Name bzw. Beschreibung eines Diagrams ändern, indem die DiagramId im Body mitgegeben wird
     *
     * @param repositoryId
     * @param bpmnDiagramUploadTO
     * @return
     */
    @PostMapping("/{repositoryId}")
    public ResponseEntity<Void> createOrUpdateDiagram(@PathVariable @NotBlank String repositoryId,
                                                      @RequestBody @Valid final BpmnDiagramUploadTO bpmnDiagramUploadTO){
        log.debug("Creating or updating Diagram");
        this.bpmnDiagramFacade.createOrUpdateDiagram(repositoryId, bpmnDiagramUploadTO);
        return ResponseEntity.ok().build();
    }

    /** Alle Diagramme innerhalb eines Repos abfragen
     *
     * @param repositoryId
     * @return
     */
    @GetMapping("/all/{repositoryId}")
    public ResponseEntity<List<BpmnDiagramTO>> getDiagramsFromRepo(@PathVariable @NotBlank String repositoryId){
        //Exceptionhandling for n.a. repositoryId
        log.debug(String.format("Returning diagrams from repository %s", repositoryId));
        return ResponseEntity.ok().body(this.bpmnDiagramFacade.getDiagramsFromRepo(repositoryId));

    }

    /** Einzelnes Diagram abfragen
     *
     * @param bpmnRepositoryId
     * @param bpmnDiagramId
     * @return
     */
    @GetMapping("/{bpmnRepositoryId}/{bpmnDiagramId}")
    public ResponseEntity<BpmnDiagramTO> getSingleDiagram(@PathVariable @NotBlank String bpmnRepositoryId,
                                                          @PathVariable @NotBlank String bpmnDiagramId){
        log.debug("Returning diagram with ID " + bpmnDiagramId);
        return ResponseEntity.ok().body(this.bpmnDiagramFacade.getSingleDiagram(bpmnRepositoryId, bpmnDiagramId));
    }

    /** Ein Diagram, inklusive aller child-versionen löschen
     *
     * @param bpmnRepositoryId
     * @param bpmnDiagramId
     * @return
     */
    @DeleteMapping("{bpmnRepositoryId}/{bpmnDiagramId}")
    @Operation(summary = "Delete one Diagram and all of its versions")
    public ResponseEntity<Void> deleteDiagram(@PathVariable @NotBlank String bpmnRepositoryId,
                                              @PathVariable @NotBlank String bpmnDiagramId){
        log.debug("Deleting Diagram with ID " + bpmnDiagramId);
        this.bpmnDiagramFacade.deleteDiagram(bpmnRepositoryId, bpmnDiagramId);
        return ResponseEntity.ok().build();
    }
}
