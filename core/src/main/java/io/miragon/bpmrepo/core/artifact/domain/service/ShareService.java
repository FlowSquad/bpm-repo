package io.miragon.bpmrepo.core.artifact.domain.service;

import io.miragon.bpmrepo.core.artifact.api.transport.ShareWithRepositoryTO;
import io.miragon.bpmrepo.core.artifact.api.transport.ShareWithTeamTO;
import io.miragon.bpmrepo.core.artifact.domain.mapper.SharedMapper;
import io.miragon.bpmrepo.core.artifact.domain.model.Artifact;
import io.miragon.bpmrepo.core.artifact.domain.model.Shared;
import io.miragon.bpmrepo.core.artifact.infrastructure.entity.SharedEntity;
import io.miragon.bpmrepo.core.artifact.infrastructure.entity.SharedId;
import io.miragon.bpmrepo.core.artifact.infrastructure.repository.SharedJpaRepository;
import io.miragon.bpmrepo.core.repository.domain.model.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShareService {
    private final SharedJpaRepository sharedJpaRepository;
    private final SharedMapper mapper;

    public Shared shareWithRepository(final ShareWithRepositoryTO shareWithRepositoryTO) {
        final Shared shared = new Shared(shareWithRepositoryTO);
        return this.saveShareWithRepository(shared);
    }

    public Shared updateShareWithRepository(final ShareWithRepositoryTO shareWithRepositoryTO) {
        final Shared shared = this.getSharedWithRepoById(shareWithRepositoryTO.getArtifactId(), shareWithRepositoryTO.getRepositoryId());
        shared.updateRole(shareWithRepositoryTO.getRole());
        return this.saveShareWithRepository(shared);
    }

    public Shared shareWithTeam(final ShareWithTeamTO shareWithTeamTO) {
        final Shared shared = new Shared(shareWithTeamTO);
        return this.saveShareWithTeam(shared);
    }

    public Shared updateShareWithTeam(final ShareWithTeamTO shareWithTeamTO) {
        final Shared shared = this.getSharedWithRepoById(shareWithTeamTO.getArtifactId(), shareWithTeamTO.getTeamId());
        shared.updateRole(shareWithTeamTO.getRole());
        return this.saveShareWithTeam(shared);
    }

    private Shared saveShareWithRepository(final Shared shared) {
        log.debug("Persisting share-relation");
        final SharedId sharedId = this.mapper.mapRepoToEmbeddable(shared.getArtifactId(), shared.getRepositoryId());
        final SharedEntity sharedEntity = this.sharedJpaRepository.save(this.mapper.mapToEntity(shared, sharedId));
        return this.mapper.mapToModel(sharedEntity);
    }

    private Shared saveShareWithTeam(final Shared shared) {
        log.debug("Persisting share-relation");
        final SharedId sharedId = this.mapper.mapTeamToEmbeddable(shared.getArtifactId(), shared.getTeamId());
        final SharedEntity sharedEntity = this.sharedJpaRepository.save(this.mapper.mapToEntity(shared, sharedId));
        return this.mapper.mapToModel(sharedEntity);
    }

    private Shared getSharedWithRepoById(final String artifactId, final String repositoryId) {
        log.debug("Querying single SharedEntity");
        return this.sharedJpaRepository.findById_ArtifactIdAndId_RepositoryId(artifactId, repositoryId).map(this.mapper::mapToModel)
                .orElseThrow();
    }

    public void deleteShareWithRepository(final String artifactId, final String repositoryId) {
        log.debug("Deleting share-relation");
        final int deletedRelations = this.sharedJpaRepository.deleteById_ArtifactIdAndId_RepositoryId(artifactId, repositoryId);
        if (deletedRelations != 1) {
            //TODO Throw custom error
            throw new RuntimeException();
        }
    }

    public void deleteShareWithTeam(final String artifactId, final String teamId) {
        log.debug("Deleting share-relation");
        final int deletedRelations = this.sharedJpaRepository.deleteById_ArtifactIdAndId_TeamId(artifactId, teamId);
        if (deletedRelations != 1) {
            //TODO Throw custom error
            throw new RuntimeException();
        }
    }


    public List<Artifact> getSharedArtifactsFromRepositories(final List<Repository> repositories) {
        log.debug("Querying all shared Artifacts from List of Repositories");
        return repositories.stream()
                .flatMap(repository -> repository.getSharedArtifacts().stream())
                .collect(Collectors.toList());
    }

}
