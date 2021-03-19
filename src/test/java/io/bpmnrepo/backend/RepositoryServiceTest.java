package io.bpmnrepo.backend;


import io.bpmnrepo.backend.repository.RepositoryBuilder;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.business.BpmnRepositoryService;
import io.bpmnrepo.backend.repository.domain.mapper.RepositoryMapper;
import io.bpmnrepo.backend.repository.domain.model.BpmnRepository;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
import io.bpmnrepo.backend.repository.infrastructure.repository.BpmnRepoJpa;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles(profiles = {"test"})
@DisplayName("Repository")
public class RepositoryServiceTest {


    @Test
    void addition(){
        assertEquals(3, 3);
    }

    /*
    @InjectMocks
    private BpmnRepositoryService bpmnRepositoryService;

    @Mock
    private RepositoryMapper repositoryMapper;

    @Mock
    private BpmnRepoJpa bpmnRepoJpa;

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
        final BpmnRepositoryTO bpmnRepositoryTO = RepositoryBuilder.buildRepoTO(REPONAME, REPODESC);
        final BpmnRepository bpmnRepository = RepositoryBuilder.buildRepo(REPOID, REPONAME, REPODESC, DATE, DATE);


*/
/*        when(this.repositoryMapper.toEntity(any())).thenReturn(new BpmnRepositoryEntity());

        this.bpmnRepositoryService.createRepository(bpmnRepositoryTO);
        verify(this.repositoryMapper, times(1)).toEntity(any());
        verify(this.bpmnRepositoryService, times(1)).saveToDb(any());*//*

    }
*/


}
