package io.bpmnrepo.backend;



import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramService;
import io.bpmnrepo.backend.diagram.domain.business.BpmnDiagramVersionService;
import io.bpmnrepo.backend.repository.AssignmentBuilder;
import io.bpmnrepo.backend.repository.BpmnRepositoryFacade;
import io.bpmnrepo.backend.repository.RepositoryBuilder;
import io.bpmnrepo.backend.repository.api.transport.NewBpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.business.AssignmentService;
import io.bpmnrepo.backend.repository.domain.business.BpmnRepositoryService;
import io.bpmnrepo.backend.repository.domain.model.Assignment;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentId;
import io.bpmnrepo.backend.repository.infrastructure.repository.AssignmentJpa;
import io.bpmnrepo.backend.repository.infrastructure.repository.BpmnRepoJpa;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.user.domain.business.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest()
public class RepositoryFacadeTest {


    @InjectMocks
    private BpmnRepositoryFacade bpmnRepositoryFacade;

    @Mock
    private BpmnRepositoryService bpmnRepositoryService;

    @Mock
    private AssignmentService assignmentService;

    @Mock
    private BpmnDiagramVersionService bpmnDiagramVersionService;

    @Mock
    private BpmnDiagramService bpmnDiagramService;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private NewBpmnRepositoryTO newBpmnRepositoryTO;

    @Mock
    private AssignmentJpa assignmentJpa;

    @Mock
    private BpmnRepoJpa bpmnRepoJpa;



    private static final String REPOID = "42";
    private static final String REPONAME = "repo name";
    private static final String REPODESC = "repository description";
    private static final String USERID = "12345";

    private static LocalDateTime DATE;


    @BeforeAll
    public static void init(){
        DATE = LocalDateTime.now();
    }


    @Test
    @DisplayName("Create new repo")
    public void createRepository(){
        newBpmnRepositoryTO = RepositoryBuilder.buildNewRepoTO(REPONAME, REPODESC);
        AssignmentId assignmentId = AssignmentBuilder.buildAssignmentId(USERID, REPOID);
        AssignmentEntity assignment = AssignmentBuilder.buildAssignment(assignmentId, RoleEnum.MEMBER);
        List<AssignmentEntity> assignmentList = new ArrayList<>();
        assignmentList.add(assignment);
        assignmentList.add(assignment);

        when(this.userService.getUserIdOfCurrentUser()).thenReturn(USERID);
        when(this.assignmentJpa.findAssignmentEntitiesByAssignmentId_UserIdEquals(USERID)).thenReturn(assignmentList);


        bpmnRepositoryFacade.createRepository(newBpmnRepositoryTO);
        verify(bpmnRepositoryService, times(1)).createRepository(any());
        verify(assignmentService, times(1)).createInitialAssignment(any());


    }

    @Test
    @DisplayName("Updating a repo")
    public void updateRepository(){
        NewBpmnRepositoryTO newBpmnRepositoryTO = RepositoryBuilder.buildNewRepoTO(REPONAME, REPODESC);

        this.bpmnRepositoryFacade.updateRepository(REPOID, newBpmnRepositoryTO);
        verify(this.authService, times(1)).checkIfOperationIsAllowed(any(), any());
        verify(this.bpmnRepositoryService, times(1)).updateRepository(any(), any());
    }


    @Test
    @DisplayName("Delete Repository")
    public void deleteRepo(){
        bpmnRepositoryFacade.deleteRepository(REPOID);
        verify(bpmnDiagramVersionService, times(1)).deleteAllByRepositoryId(REPOID);
        verify(bpmnDiagramService, times(1)).deleteAllByRepositoryId(REPOID);
        verify(bpmnRepositoryService, times(1)).deleteRepository(REPOID);
        verify(assignmentService, times(1)).deleteAllByRepositoryId(REPOID);
    }
}
