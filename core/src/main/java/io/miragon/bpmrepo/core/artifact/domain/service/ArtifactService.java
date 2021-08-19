package io.miragon.bpmrepo.core.artifact.domain.service;

import io.miragon.bpmrepo.core.artifact.domain.mapper.ArtifactMapper;
import io.miragon.bpmrepo.core.artifact.domain.model.Artifact;
import io.miragon.bpmrepo.core.artifact.domain.model.ArtifactUpdate;
import io.miragon.bpmrepo.core.artifact.infrastructure.entity.ArtifactEntity;
import io.miragon.bpmrepo.core.artifact.infrastructure.repository.ArtifactJpaRepository;
import io.miragon.bpmrepo.core.repository.domain.model.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtifactService {

    private final ArtifactJpaRepository artifactJpaRepository;
    private final ArtifactMapper mapper;

    public Artifact createArtifact(final Artifact artifact) {
        return this.saveArtifact(artifact);
    }

    public Artifact updateArtifact(final Artifact artifact, final ArtifactUpdate artifactUpdate) {
        log.debug("Persisting Update");
        artifact.updateArtifact(artifactUpdate);
        return this.saveArtifact(artifact);
    }

    public Optional<List<Artifact>> getArtifactsByRepo(final String repositoryId) {
        log.debug("Querying Artifacts in Repository");
        return this.artifactJpaRepository.findAllByRepositoryIdOrderByUpdatedDateDesc(repositoryId)
                .map(this.mapper::mapToModel);
    }

    public Artifact getArtifactById(final String artifactId) {
        log.debug("Querying single Artifact");
        return this.artifactJpaRepository.findById(artifactId).map(this.mapper::mapToModel)
                .orElseThrow();
    }

    public ArtifactEntity getArtifactEntityById(final String artifactId) {
        log.debug("Querying single Artifact");
        return this.artifactJpaRepository.findById(artifactId)
                .orElseThrow();
    }

    public Optional<List<Artifact>> getAllArtifactsById(final List<String> artifactIds) {
        log.debug("Querying List of Artifacts");
        return this.artifactJpaRepository.findAllByIdIn(artifactIds).map(this.mapper::mapToModel);
    }

    public Optional<List<Artifact>> getAllByRepositoryIds(final List<String> repositoryIds) {
        log.debug("Querying all Artifacts in List of Repositories");
        return this.artifactJpaRepository.findAllByRepositoryIdIn(repositoryIds).map(this.mapper::mapToModel);
    }

    public List<Artifact> getSharedArtifactsFromRepository(final Repository repository) {
        log.debug("Querying all shared Artifacts from Repository");
        return repository.getSharedArtifacts();
    }

    public void updateUpdatedDate(final String artifactId) {
        final Artifact artifact = this.getArtifactById(artifactId);
        artifact.updateDate();
        this.saveArtifact(artifact);
    }

    private Artifact saveArtifact(final Artifact artifact) {
        log.debug("Persisting Artifact");
        final ArtifactEntity savedArtifact = this.artifactJpaRepository.save(this.mapper.mapToEntity(artifact));
        return this.mapper.mapToModel(savedArtifact);
    }

    public Integer countExistingArtifacts(final String repositoryId) {
        log.debug("Querying number of existing Artifacts");
        return this.artifactJpaRepository.countAllByRepositoryId(repositoryId);
    }

    public void deleteArtifact(final String artifactId) {
        this.artifactJpaRepository.deleteById(artifactId);
        log.info(String.format("Deleted Artifact with ID %s", artifactId));
    }

    public void deleteAllByRepositoryId(final String repositoryId) {
        final int deletedArtifacts = this.artifactJpaRepository.deleteAllByRepositoryId(repositoryId);
        log.debug(String.format("Deleted %s artifacts", deletedArtifacts));
    }

    public Optional<List<Artifact>> getRecent(final List<String> assignedRepositoryIds) {
        log.debug("Querying recent Artifacts");
        //TODO Improve performance -> save in separate db
        return this.artifactJpaRepository.findTop10ByRepositoryIdInOrderByUpdatedDateDesc(assignedRepositoryIds)
                .map(this.mapper::mapToModel);
    }

    public Artifact updatePreviewSVG(final String artifactId, final String svgPreview) {
        log.debug("Persisting Update");
        final Artifact artifact = this.getArtifactById(artifactId);
        artifact.updateSvgPreview(svgPreview);
        return this.saveArtifact(artifact);
    }

    public Optional<List<Artifact>> searchArtifacts(final List<String> assignedRepoIds, final String typedTitle) {
        log.debug("Querying Artifacts that match the search string");
        return this.artifactJpaRepository
                .findAllByRepositoryIdInAndNameStartsWithIgnoreCase(assignedRepoIds, typedTitle).map(this.mapper::mapToModel);
    }

    public Artifact lockArtifact(final String artifactId, final String username) {
        log.debug("Persisting Artifact-Lock for Artifact {} for User {}", artifactId, username);
        final Artifact artifact = this.getArtifactById(artifactId);
        artifact.lock(username);
        return this.saveArtifact(artifact);
    }

    public Artifact unlockArtifact(final String artifactId) {
        log.debug("Releasing Artifact-Lock for Artifact {}", artifactId);
        final Artifact artifact = this.getArtifactById(artifactId);
        artifact.unlock();
        return ArtifactService.this.saveArtifact(artifact);
    }

    public Optional<List<Artifact>> getByRepoIdAndType(final String repositoryId, final String type) {
        log.debug("Querying artifacts of Type {} from Repository {}", type, repositoryId);
        return this.artifactJpaRepository.findAllByRepositoryIdAndFileTypeIgnoreCase(repositoryId, type).map(this.mapper::mapToModel);
    }
}
