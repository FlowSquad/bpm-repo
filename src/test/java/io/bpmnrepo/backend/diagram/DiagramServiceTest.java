package io.bpmnrepo.backend.diagram;

import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.domain.mapper.DiagramMapper;
import io.bpmnrepo.backend.diagram.infrastructure.repository.BpmnDiagramJpa;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DiagramServiceTest {

    @InjectMocks
    private BpmnDiagramService bpmnDiagramService;

    @Mock
    private BpmnDiagramJpa bpmnDiagramJpa;

    @Mock
    private DiagramMapper diagramMapper;


    @Test
    public void createDiagram(){

    }
}
