package io.bpmnrepo.backend;



import io.bpmnrepo.backend.repository.BpmnRepositoryFacade;
import io.bpmnrepo.backend.repository.RepositoryBuilder;
import io.bpmnrepo.backend.repository.api.resource.BpmnRepositoryController;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.api.transport.NewBpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.business.BpmnRepositoryService;
import io.bpmnrepo.backend.repository.domain.mapper.RepositoryMapper;
import io.bpmnrepo.backend.repository.domain.model.BpmnRepository;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import io.bpmnrepo.backend.repository.infrastructure.repository.BpmnRepoJpa;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest()
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles(profiles = {"test"})
//@DisplayName("Repository")
public class RepositoryServiceTest {

/*

    public RepositoryServiceTest() {
    }

    @Test
    void addition(){
        assertEquals(3, 3);
    }


    @Mock
    private BpmnRepositoryService bpmnRepositoryService;

    @Mock
    private RepositoryMapper repositoryMapper;

    @Mock
    private BpmnRepoJpa bpmnRepoJpa;

    @Mock
    private NewBpmnRepositoryTO newBpmnRepositoryTO;

    @Mock
    private BpmnRepositoryEntity bpmnRepositoryEntity;

    @Mock
    private BpmnRepository bpmnRepository;

    @Mock
    private BpmnRepositoryFacade bpmnRepositoryFacade;


    private static final String REPOID = "42";
    private static final String REPONAME = "repo name";
    private static final String REPODESC = "repository description";
    private static LocalDateTime DATE;


    @BeforeAll
    public static void init(){
        DATE = LocalDateTime.now();
    }


    @Test
    @DisplayName("Create new repo")
    public void createRepository(){
        newBpmnRepositoryTO = RepositoryBuilder.buildNewRepoTO(REPONAME, REPODESC);
        bpmnRepository = RepositoryBuilder.buildRepo(REPOID, REPONAME, REPODESC, DATE, DATE);
        bpmnRepositoryEntity = new BpmnRepositoryEntity(REPOID, REPONAME, REPODESC, DATE, DATE);


        when(repositoryMapper.toModel(newBpmnRepositoryTO)).thenReturn(bpmnRepository);
        when(repositoryMapper.toEntity(any())).thenReturn(new BpmnRepositoryEntity());
        when(bpmnRepositoryService.saveToDb(any())).thenReturn(bpmnRepository);

        bpmnRepositoryService.createRepository(newBpmnRepositoryTO);
        //verify(this.repositoryMapper, times(1)).toModel(newBpmnRepositoryTO);
        //verify(this.repositoryMapper, times(1)).toEntity(any());
        verify(bpmnRepositoryService, times(1)).saveToDb(any());
        String bpmnRepositoryId = bpmnRepositoryService.createRepository(newBpmnRepositoryTO);

        assertEquals(bpmnRepositoryId, notNull());
    }

    @Test
    @DisplayName("Delete Repository")
    public void deleteRepo(){
        BpmnRepositoryController bpmnRepositoryController = new BpmnRepositoryController(bpmnRepositoryFacade);

*/
/*
        when(this.bpmnRepositoryFacade.deleteRepository(REPOID)).thenReturn(any());
*//*



        when()

        verify(bpmnRepositoryFacade, times(1)).deleteRepository(REPOID);


    }
*/


}
