package io.bpmnrepo.backend.repository.model;

import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
import io.bpmnrepo.backend.repository.RepositoryBuilder;
import io.bpmnrepo.backend.repository.api.transport.BpmnRepositoryTO;
import io.bpmnrepo.backend.repository.api.transport.NewBpmnRepositoryTO;
import io.bpmnrepo.backend.repository.domain.mapper.RepositoryMapper;
import io.bpmnrepo.backend.repository.domain.model.BpmnRepository;
import io.bpmnrepo.backend.repository.infrastructure.entity.BpmnRepositoryEntity;
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
public class RepositoryModelTest {

    private static final String REPOID = "42";
    private static final String REPONAME = "repo name";
    private static final String NEWREPONAME = "new name";
    private static final String REPODESC = "repository description";
    private static final String NEWREPODESC = "new description";
    private static LocalDateTime DATE;


    @BeforeAll
    public static void init(){
        DATE = LocalDateTime.now();
    }


    @Test
    public void updateRepository(){
        NewBpmnRepositoryTO newBpmnRepositoryTOName = RepositoryBuilder.buildNewRepoTO(NEWREPONAME, null);
        BpmnRepository bpmnRepository = RepositoryBuilder.buildRepo(REPOID, REPONAME, REPODESC, DATE, DATE);

        //update name only
        bpmnRepository.updateRepo(newBpmnRepositoryTOName);
        assertEquals(NEWREPONAME, bpmnRepository.getBpmnRepositoryName());
        assertEquals(REPODESC, bpmnRepository.getBpmnRepositoryDescription());
        //update description only
        bpmnRepository = RepositoryBuilder.buildRepo(REPOID, REPONAME, REPODESC, DATE, DATE);
        NewBpmnRepositoryTO newBpmnRepositoryTODesc = RepositoryBuilder.buildNewRepoTO(null, NEWREPODESC);
        bpmnRepository.updateRepo(newBpmnRepositoryTODesc);
        assertEquals(NEWREPODESC, bpmnRepository.getBpmnRepositoryDescription());
        assertEquals(REPONAME, bpmnRepository.getBpmnRepositoryName());
        //update both
        bpmnRepository = RepositoryBuilder.buildRepo(REPOID, REPONAME, REPODESC, DATE, DATE);
        NewBpmnRepositoryTO newBpmnRepositoryTOBoth = RepositoryBuilder.buildNewRepoTO(NEWREPONAME, NEWREPODESC);
        bpmnRepository.updateRepo(newBpmnRepositoryTOBoth);
        assertEquals(NEWREPONAME, bpmnRepository.getBpmnRepositoryName());
        assertEquals(NEWREPODESC, bpmnRepository.getBpmnRepositoryDescription());
    }
}
