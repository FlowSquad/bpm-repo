package io.miragon.bpmrepo.core.artifact.domain.service;

import io.miragon.bpmrepo.core.artifact.domain.mapper.ArtifactMapper;
import io.miragon.bpmrepo.core.artifact.domain.model.Artifact;
import io.miragon.bpmrepo.core.artifact.domain.model.ArtifactUpdate;
import io.miragon.bpmrepo.core.artifact.infrastructure.entity.ArtifactEntity;
import io.miragon.bpmrepo.core.artifact.infrastructure.repository.ArtifactJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtifactService {

    private final ArtifactJpaRepository artifactJpaRepository;
    private final ArtifactMapper mapper;

    public Artifact createArtifact(final Artifact artifact) {
        return this.saveArtifact(artifact);
    }

    public Artifact updateArtifact(final String artifactId, final ArtifactUpdate artifactUpdate) {
        final Artifact artifact = this.getArtifactById(artifactId);
        artifact.updateArtifact(artifactUpdate);
        return this.saveArtifact(artifact);
    }

    public List<Artifact> getArtifactsByRepo(final String repositoryId) {
        final List<ArtifactEntity> artifacts = this.artifactJpaRepository.findAllByRepositoryIdOrderByUpdatedDateDesc(repositoryId);
        return this.mapper.mapToModel(artifacts);
    }

    public Artifact getArtifactById(final String artifactId) {
        return this.artifactJpaRepository.findById(artifactId)
                .map(this.mapper::mapToModel)
                .orElseThrow();
    }

    public Optional<List<Artifact>> getAllArtifactsById(final List<String> artifactIds) {
        return this.artifactJpaRepository.findAllByIdIn(artifactIds).map(this.mapper::mapToModel);
    }

    public Optional<List<Artifact>> getAllByRepositoryIds(final List<String> repositoryIds) {
        return this.artifactJpaRepository.findAllByRepositoryIdIn(repositoryIds).map(this.mapper::mapToModel);
    }

    public void updateUpdatedDate(final String artifactId) {
        final Artifact artifact = this.getArtifactById(artifactId);
        artifact.updateDate();
        this.saveArtifact(artifact);
    }

    private Artifact saveArtifact(final Artifact bpmnArtifact) {
        val savedArtifact = this.artifactJpaRepository.save(this.mapper.mapToEntity(bpmnArtifact));
        return this.mapper.mapToModel(savedArtifact);
    }

    public Integer countExistingArtifacts(final String repositoryId) {
        return this.artifactJpaRepository.countAllByRepositoryId(repositoryId);
    }

    public void deleteArtifact(final String artifactId) {
        this.artifactJpaRepository.deleteById(artifactId);
        log.info(String.format("Deleted %s Artifact", artifactId));
    }

    public void deleteAllByRepositoryId(final String bpmnRepositoryId) {
        final int deletedArtifacts = this.artifactJpaRepository.deleteAllByRepositoryId(bpmnRepositoryId);
        log.debug(String.format("Deleted %s artifacts", deletedArtifacts));
    }

    public List<Artifact> getRecent(final List<String> assignments) {
        //TODO Improve performance -> save in separate db
        final List<ArtifactEntity> artifacts = assignments.stream()
                .map(this.artifactJpaRepository::findAllByRepositoryIdOrderByUpdatedDateDesc)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(a -> Timestamp.valueOf(a.getUpdatedDate())))
                .collect(Collectors.toList());
        Collections.reverse(artifacts);
        return this.mapper.mapToModel(artifacts.subList(0, Math.min(artifacts.size(), 10)));
    }

    public void updatePreviewSVG(final String artifactId, final String svgPreview) {
        final Artifact artifact = this.getArtifactById(artifactId);
        artifact.updateSvgPreview(svgPreview);
        this.saveArtifact(artifact);
    }

    public List<Artifact> searchArtifacts(final List<String> assignedRepoIds, final String typedTitle) {
        final List<ArtifactEntity> assignedArtifacts = this.artifactJpaRepository
                .findAllByRepositoryIdInAndNameStartsWithIgnoreCase(assignedRepoIds, typedTitle);
        return this.mapper.mapToModel(assignedArtifacts);
    }

    public void lockArtifact(final String artifactId, final String username) {
        final Artifact artifact = this.getArtifactById(artifactId);
        artifact.lock(username);
        this.saveArtifact(artifact);
    }

    public void unlockArtifact(final String artifactId) {
        final Artifact artifact = this.getArtifactById(artifactId);
        artifact.unlock();
        ArtifactService.this.saveArtifact(artifact);
    }

    public Optional<List<Artifact>> getByRepoIdAndType(final String repositoryId, final String type) {
        log.debug("Querying artifacts of Type {} from Repository {}", type, repositoryId);
        return this.artifactJpaRepository.findAllByRepositoryIdAndFileTypeIgnoreCase(repositoryId, type).map(this.mapper::mapToModel);
    }
}
