package io.bpmnrepo.backend.diagram;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramUploadTO;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramJpa;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.VerifyRelationService;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class DiagramFacadeTest {

    @InjectMocks
    private BpmnDiagramFacade bpmnDiagramFacade;

    @Mock
    private AuthService authService;
    @Mock
    private VerifyRelationService verifyRelationService;
    @Mock
    private BpmnDiagramService bpmnDiagramService;
    @Mock
    private BpmnDiagramVersionService bpmnDiagramVersionService;
    @Mock
    private BpmnDiagramJpa bpmnDiagramJpa;

    private static final String DIAGRAMID = "123456";
    private static final String REPOID = "01";
    private static final String DIAGRAMNAME = "TestDiagram";
    private static final String DIAGRAMDESC = "SomeDescription";
    private static LocalDateTime DATE;

    @BeforeAll
    public static void init(){
        DATE = LocalDateTime.now();
    }

    @Test
    public void createOrUpdateDiagram(){
        BpmnDiagramUploadTO bpmnDiagramUploadTO = DiagramBuilder.buildUploadTO(DIAGRAMID, DIAGRAMNAME, DIAGRAMDESC);

        bpmnDiagramFacade.createOrUpdateDiagram(REPOID, bpmnDiagramUploadTO);
        verify(authService, times(1)).checkIfOperationIsAllowed(REPOID, RoleEnum.MEMBER);


    }

    @Test
    public void getSingleDiagram(){
        bpmnDiagramFacade.getSingleDiagram(REPOID, DIAGRAMID);
        verify(verifyRelationService, times(1)).verifyDiagramIsInSpecifiedRepository(REPOID, DIAGRAMID);
        verify(authService, times(1)).checkIfOperationIsAllowed(REPOID, RoleEnum.VIEWER);
        verify(bpmnDiagramService, times(1)).getSingleDiagram(DIAGRAMID);
    }

    @Test
    public void deleteDiagram(){
        bpmnDiagramFacade.deleteDiagram(REPOID, DIAGRAMID);
        verify(verifyRelationService, times(1)).verifyDiagramIsInSpecifiedRepository(REPOID, DIAGRAMID);
        verify(authService, times(1)).checkIfOperationIsAllowed(REPOID, RoleEnum.ADMIN);
        verify(bpmnDiagramVersionService, times(1)).deleteAllByDiagramId(DIAGRAMID);
        verify(bpmnDiagramService, times(1)).deleteDiagram(DIAGRAMID);
    }
}
