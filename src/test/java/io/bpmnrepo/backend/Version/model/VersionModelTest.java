package io.bpmnrepo.backend.Version.model;

import io.bpmnrepo.backend.Version.VersionBuilder;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramUploadTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionUploadTO;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagramVersion;
import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
import io.bpmnrepo.backend.shared.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VersionModelTest {

    private static final String REPOID = "42";
    private static final String DIAGRAMID = "001";
    private static final String VERSIONID = "v-01";
    private static final String FILESTRING = "somexmlString";
    private static final byte[] FILEBYTES = FILESTRING.getBytes();
    private static final Integer RELEASE = 1;
    private static final Integer MILESTONE = 2;
    private static final String COMMENT = "versionComment";
    private static final String UPDATEDCOMMENT = "new comment";
    private static final SaveTypeEnum saveTypeRelease = SaveTypeEnum.RELEASE;
    private static final SaveTypeEnum saveTypeMileStone = SaveTypeEnum.MILESTONE;

    @Test
    public void updateVersion(){
        final BpmnDiagramVersion bpmnDiagramVersion = VersionBuilder.buildVersion(VERSIONID, DIAGRAMID, REPOID, COMMENT, RELEASE, MILESTONE, FILEBYTES, saveTypeMileStone);
        final BpmnDiagramVersionTO bpmnDiagramVersionTO = VersionBuilder.buildVersionTO(VERSIONID, DIAGRAMID, REPOID, UPDATEDCOMMENT, RELEASE, MILESTONE, FILESTRING, saveTypeMileStone);

        //first update: MILESTONE - check version numbers
        bpmnDiagramVersion.updateVersion(bpmnDiagramVersionTO);
        assertEquals(bpmnDiagramVersion.getBpmnDiagramVersionRelease(), RELEASE);
        assertEquals(bpmnDiagramVersion.getBpmnDiagramVersionMilestone(), MILESTONE + 1);
        //second update: MILESTONE - check version numbers
        bpmnDiagramVersion.updateVersion(bpmnDiagramVersionTO);
        assertEquals(bpmnDiagramVersion.getBpmnDiagramVersionRelease(), RELEASE);
        assertEquals(bpmnDiagramVersion.getBpmnDiagramVersionMilestone(), MILESTONE + 2);

        bpmnDiagramVersionTO.setSaveType(saveTypeRelease);
        bpmnDiagramVersionTO.setBpmnDiagramVersionComment(null);
        //third update: RELEASE - check version numbers and if the old comment has been adopted
        bpmnDiagramVersion.updateVersion(bpmnDiagramVersionTO);
        assertEquals(RELEASE + 1, bpmnDiagramVersion.getBpmnDiagramVersionRelease());
        assertEquals(0, bpmnDiagramVersion.getBpmnDiagramVersionMilestone());
        assertEquals(UPDATEDCOMMENT, bpmnDiagramVersion.getBpmnDiagramVersionComment());

    }
    @Test
    public void createVersion(){
        final BpmnDiagramVersionUploadTO bpmnDiagramVersionUploadTO = VersionBuilder.buildVersionUploadTO(COMMENT, FILESTRING, saveTypeRelease);
        final BpmnDiagramVersionTO bpmnDiagramVersionTO = new BpmnDiagramVersionTO(REPOID, DIAGRAMID, bpmnDiagramVersionUploadTO);
        final BpmnDiagramVersion bpmnDiagramVersion = new BpmnDiagramVersion(bpmnDiagramVersionTO);

        assertEquals(REPOID, bpmnDiagramVersion.getBpmnRepositoryId());
        assertEquals(COMMENT, bpmnDiagramVersion.getBpmnDiagramVersionComment());
        //assertEquals(FILEBYTES.toString(), bpmnDiagramVersion.getBpmnDiagramVersionFile().toString());
        assertEquals(1, bpmnDiagramVersion.getBpmnDiagramVersionRelease());
        assertEquals(0, bpmnDiagramVersion.getBpmnDiagramVersionMilestone());
    }

}
