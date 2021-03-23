package io.bpmnrepo.backend.Version;

import io.bpmnrepo.backend.diagram.api.transport.BpmnDiagramVersionTO;
import io.bpmnrepo.backend.diagram.domain.model.BpmnDiagramVersion;
import io.bpmnrepo.backend.diagram.infrastructure.SaveTypeEnum;
import io.bpmnrepo.backend.diagram.infrastructure.entity.BpmnDiagramVersionEntity;

public class VersionBuilder {

    public static BpmnDiagramVersion buildVersion(String versionId, String diagramId, String repoId, String comment, Integer release, Integer milestone, byte[] file, SaveTypeEnum saveTypeEnum){
        return BpmnDiagramVersion.builder()
                .bpmnDiagramVersionId(versionId)
                .bpmnDiagramId(diagramId)
                .bpmnRepositoryId(repoId)
                .bpmnDiagramVersionComment(comment)
                .bpmnDiagramVersionRelease(release)
                .bpmnDiagramVersionMilestone(milestone)
                .bpmnDiagramVersionFile(file)
                .saveType(saveTypeEnum)
                .build();
    }

    public static BpmnDiagramVersionTO buildVersionTO(String versionId, String diagramId, String repoId, String comment, Integer release, Integer milestone, String file, SaveTypeEnum saveTypeEnum){
        return BpmnDiagramVersionTO.builder()
                .bpmnDiagramVersionId(versionId)
                .bpmnDiagramId(diagramId)
                .bpmnRepositoryId(repoId)
                .bpmnDiagramVersionComment(comment)
                .bpmnDiagramVersionRelease(release)
                .bpmnDiagramVersionMilestone(milestone)
                .bpmnAsXML(file)
                .saveType(saveTypeEnum)
                .build();
    }

    public static BpmnDiagramVersionEntity buildVersionEntity(String versionId, String diagramId, String repoId, String comment, Integer release, Integer milestone, byte[] file, SaveTypeEnum saveTypeEnum){
        return BpmnDiagramVersionEntity.builder()
                .bpmnDiagramVersionId(versionId)
                .bpmnDiagramId(diagramId)
                .bpmnRepositoryId(repoId)
                .bpmnDiagramVersionComment(comment)
                .bpmnDiagramVersionRelease(release)
                .bpmnDiagramVersionMilestone(milestone)
                .bpmnDiagramVersionFile(file)
                .saveType(saveTypeEnum)
                .build();
    }

}
