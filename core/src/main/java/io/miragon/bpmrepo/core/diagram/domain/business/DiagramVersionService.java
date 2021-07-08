package io.miragon.bpmrepo.core.diagram.domain.business;

import io.miragon.bpmrepo.core.diagram.domain.enums.SaveTypeEnum;
import io.miragon.bpmrepo.core.diagram.domain.mapper.VersionMapper;
import io.miragon.bpmrepo.core.diagram.domain.model.DiagramVersion;
import io.miragon.bpmrepo.core.diagram.infrastructure.entity.DiagramVersionEntity;
import io.miragon.bpmrepo.core.diagram.infrastructure.repository.DiagramVersionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiagramVersionService {

    private final DiagramVersionJpaRepository diagramVersionJpaRepository;
    private final VersionMapper mapper;

    public String updateVersion(final DiagramVersion diagramVersion) {
        final DiagramVersion latestVersion = this.getLatestVersion(diagramVersion.getDiagramId());
        latestVersion.updateVersion(diagramVersion);
        return this.saveToDb(latestVersion);
    }

    public String createNewVersion(final DiagramVersion diagramVersion) {
        final DiagramVersion latestVersion = this.getLatestVersion(diagramVersion.getDiagramId());
        diagramVersion.updateMilestone(latestVersion.getMilestone() + 1);
        return this.saveToDb(diagramVersion);
    }

    public String createInitialVersion(final DiagramVersion diagramVersion) {
        diagramVersion.initXml();
        diagramVersion.updateMilestone(1);
        return this.saveToDb(diagramVersion);
    }

    public List<DiagramVersion> getAllVersions(final String diagramId) {
        final List<DiagramVersionEntity> diagramVersionEntities = this.diagramVersionJpaRepository.findAllByDiagramId(diagramId);
        return this.mapper.mapToModel(diagramVersionEntities);
    }

    public DiagramVersion getLatestVersion(final String diagramId) {
        return this.diagramVersionJpaRepository
                .findFirstByDiagramIdOrderByMilestoneDesc(diagramId)
                .map(this.mapper::mapToModel)
                .orElseThrow();
    }

    public DiagramVersion getVersion(final String diagramVersionId) {
        return this.diagramVersionJpaRepository.findById(diagramVersionId)
                .map(this.mapper::mapToModel)
                .orElseThrow();
    }

    public String saveToDb(final DiagramVersion bpmnDiagramVersion) {
        log.debug("Save diagram version " + bpmnDiagramVersion);
        final DiagramVersionEntity savedVersion = this.diagramVersionJpaRepository.save(this.mapper.mapToEntity(bpmnDiagramVersion));
        return (savedVersion.getId());
    }

    public void deleteAllByDiagramId(final String diagramId) {
        final int deletedVersions = this.diagramVersionJpaRepository.deleteAllByDiagramId(diagramId);
        log.debug(String.format("Deleted %s versions", deletedVersions));
    }

    public void deleteAllByRepositoryId(final String repositoryId) {
        final int deletedVersions = this.diagramVersionJpaRepository.deleteAllByRepositoryId(repositoryId);
        log.debug(String.format("Deleted %s versions", deletedVersions));
    }

    public void deleteAutosavedVersions(final String bpmnRepositoryId, final String bpmnDiagramId) {
        this.diagramVersionJpaRepository.deleteAllByRepositoryIdAndDiagramIdAndSaveType(bpmnRepositoryId, bpmnDiagramId, SaveTypeEnum.AUTOSAVE);
    }

    public ByteArrayResource downloadVersion(final String diagramName, final String diagramVersionId) {
        final DiagramVersion diagramVersion = this.getVersion(diagramVersionId);
        final ByteArrayResource resource = new ByteArrayResource(diagramVersion.getXml().getBytes());
        return resource;
    }
}
