package io.bpmnrepo.backend.assignment;

import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
import io.bpmnrepo.backend.repository.api.transport.AssignmentDeletionTO;
import io.bpmnrepo.backend.repository.api.transport.AssignmentTO;
import io.bpmnrepo.backend.repository.api.transport.AssignmentWithUserNameTO;
import io.bpmnrepo.backend.repository.domain.business.AssignmentService;
import io.bpmnrepo.backend.repository.domain.mapper.AssignmentMapper;
import io.bpmnrepo.backend.repository.domain.model.Assignment;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentEntity;
import io.bpmnrepo.backend.repository.infrastructure.entity.AssignmentId;
import io.bpmnrepo.backend.repository.infrastructure.repository.AssignmentJpa;
import io.bpmnrepo.backend.shared.AuthService;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import io.bpmnrepo.backend.user.domain.business.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AssignmentTest {

    @InjectMocks
    private AssignmentService assignmentService;

    @Mock
    private AssignmentJpa assignmentJpa;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private AssignmentMapper mapper;


    private static final String REPOID = "42";
    private static final String DIAGRAMID = "001";
    private static final String VERSIONID = "v-01";
    private static final String REPONAME = "repo name";
    private static final String REPODESC = "repository description";
    private static final String USERID = "12345";
    private static final String ASSIGNEDUSERID = "5678";
    private static final String DELETEDUSERID = "5678";
    private static final String USERNAME = "user";
    private static final RoleEnum ROLE = RoleEnum.ADMIN;
    private static final RoleEnum ASSIGNEDROLE = RoleEnum.MEMBER;
    private static final RoleEnum ROLEBEFORE = RoleEnum.VIEWER;

    private static LocalDateTime DATE;
    private static final String COMMENT = "versionComment";
    private static final String FILESTRING = "someStringForXML";
    private static final SaveTypeEnum saveType = SaveTypeEnum.AUTOSAVE;


 /*   @BeforeAll
    public void init(){

    }

    @Test
    public void createOrUpdateAssignment(){
        AssignmentWithUserNameTO assignmentWithUserNameTO = AssignmentBuilder.buildAssignmentWithUserName(REPOID, USERNAME, ROLE);
        AssignmentId assignmentId = AssignmentBuilder.buildAssignmentId(USERID, REPOID);
        AssignmentTO assignmentTO = AssignmentBuilder.buildAssignmentTO(REPOID, USERID, ROLE);
        Assignment assignment = AssignmentBuilder.buildAssignment(USERID, REPOID, ROLE);
        AssignmentEntity assignmentEntity = AssignmentBuilder.buildAssignmentEntity(assignmentId, ROLE);


        when(this.mapper.toModel(assignmentTO)).thenReturn(assignment);
        when(this.mapper.toEmbeddable(USERID, REPOID)).thenReturn(assignmentId);
        when(this.mapper.toEntity(assignment, assignmentId)).thenReturn(assignmentEntity);
        //when(this.mapper.toEntity(assignment))

        this.assignmentService.createOrUpdateAssignment(assignmentWithUserNameTO);
        verify(this.authService, times(1)).checkIfOperationIsAllowed(REPOID, ROLE);
        verify(this.userService, times(1)).getUserIdByUsername(USERNAME);
        verify(this.assignmentJpa, times(1)).findByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(REPOID, USERNAME);
        verify(this.mapper, times(1)).toModel(assignmentTO);

    }

    @Test
    public void createAssignment(){
        AssignmentId assignmentId = AssignmentBuilder.buildAssignmentId(USERID, REPOID);
        AssignmentTO assignmentTO = AssignmentBuilder.buildAssignmentTO(REPOID, USERID, ROLE);
        Assignment assignment = AssignmentBuilder.buildAssignment(USERID, REPOID, ROLE);
        AssignmentEntity assignmentEntity = AssignmentBuilder.buildAssignmentEntity(assignmentId, ROLE);

        final ArgumentCaptor<AssignmentEntity> captor = ArgumentCaptor.forClass(AssignmentEntity.class);
        when(this.mapper.toModel(assignmentTO)).thenReturn(assignment);
        when(this.mapper.toEmbeddable(USERID, REPOID)).thenReturn(assignmentId);
        when(this.mapper.toEntity(assignment, assignmentId)).thenReturn(assignmentEntity);

        this.assignmentService.createAssignment(assignmentTO);
        verify(this.mapper, times(1)).toModel(any());
        verify(this.mapper, times(1)).toEntity(assignment, assignmentId);
        verify(this.assignmentJpa, times(1)).save(captor.capture());

        final AssignmentEntity savedAssignment = captor.getValue();
        assertNotNull(savedAssignment);
        assertEquals(assignmentEntity.getAssignmentId(), assignmentId);
    }

    @Test
    public void updateAssignment(){
        AssignmentTO assignmentTO = AssignmentBuilder.buildAssignmentTO(REPOID, ASSIGNEDUSERID, ASSIGNEDROLE);
        Assignment assignment = AssignmentBuilder.buildAssignment(ASSIGNEDUSERID, REPOID, ASSIGNEDROLE);
        AssignmentId assignmentId = AssignmentBuilder.buildAssignmentId(ASSIGNEDUSERID, REPOID);
        AssignmentEntity assignmentEntity = AssignmentBuilder.buildAssignmentEntity(assignmentId, ROLE);

        AssignmentEntity assignmentEntityBefore = AssignmentBuilder.buildAssignmentEntity(assignmentId, ROLEBEFORE);
        AssignmentEntity assignmentEntityAfter = AssignmentBuilder.buildAssignmentEntity(assignmentId, ASSIGNEDROLE);

        System.out.println("_________________-");
        System.out.println(assignmentEntityBefore.getClass());
        //when(this.assignmentService.getAssignmentEntity(REPOID, USERID)).thenReturn(assignmentEntity);
        when(this.assignmentJpa.findByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(REPOID, ASSIGNEDUSERID)).thenReturn(assignmentEntityBefore);
        when(this.assignmentService.getUserRole(REPOID, USERID)).thenReturn(ROLE);
        when(this.assignmentService.getUserRole(REPOID, ASSIGNEDUSERID)).thenReturn(ROLEBEFORE);

        this.assignmentService.updateAssignment(assignmentTO);
        verify(this.authService, times(1)).checkIfOperationIsAllowed(REPOID, ROLE);
        verify(this.mapper, times(1)).toEmbeddable(ASSIGNEDUSERID, REPOID);
        verify(this.mapper, times(1)).toEntity(assignment, assignmentId);
        verify(this.assignmentJpa, times(1)).save(assignmentEntityAfter);
    }

    @Test
    public void getAssignmentEntity(){
        this.assignmentService.getAssignmentEntity(REPOID, USERID);
        verify(this.assignmentJpa, times(1)).findByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(REPOID, USERID);
    }

    @Test
    public void deleteAssignment(){
        AssignmentDeletionTO assignmentDeletionTO = AssignmentBuilder.buildAssignmentDeletion(REPOID, USERNAME);
        AssignmentId assignmentId = AssignmentBuilder.buildAssignmentId(ASSIGNEDUSERID, REPOID);
        AssignmentEntity assignmentEntity = AssignmentBuilder.buildAssignmentEntity(assignmentId, ROLE);

        when(this.assignmentJpa.findByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(any(), any())).thenReturn(assignmentEntity);
        //when(this.assignmentService.getAssignmentEntity(REPOID, USERID)).thenReturn(assignmentEntity);
        //when(this.assignmentService.getUserRole(REPOID, USERID)).thenReturn(ROLE);
       // when(this.assignmentService.getUserRole(REPOID, ASSIGNEDUSERID)).thenReturn(ASSIGNEDROLE);
        when(this.userService.getUserIdOfCurrentUser()).thenReturn(USERID);
        when(this.userService.getUserIdByUsername(any())).thenReturn(ASSIGNEDUSERID);
        when(this.assignmentService.getAssignmentEntity(REPOID, ASSIGNEDUSERID)).thenReturn(assignmentEntity);

        this.assignmentService.deleteAssignment(REPOID, DELETEDUSERID);
        verify(this.userService, times(1)).getUserIdByUsername(any());
        verify(this.userService, times(1)).getUserIdOfCurrentUser();
        verify(this.authService, times(1)).checkIfOperationIsAllowed(REPOID, ROLE);
        verify(this.assignmentJpa, times(1)).deleteAssignmentEntityByAssignmentId_BpmnRepositoryIdAndAssignmentId_UserId(REPOID, ASSIGNEDUSERID);

    }
*/
}
