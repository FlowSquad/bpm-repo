package io.miragon.bpmrepo.core.artifact.domain.facade;

import io.miragon.bpmrepo.core.artifact.api.transport.ShareWithRepositoryTO;
import io.miragon.bpmrepo.core.artifact.api.transport.ShareWithTeamTO;
import io.miragon.bpmrepo.core.artifact.domain.model.Artifact;
import io.miragon.bpmrepo.core.artifact.domain.model.Shared;
import io.miragon.bpmrepo.core.artifact.domain.service.ArtifactService;
import io.miragon.bpmrepo.core.artifact.domain.service.ShareService;
import io.miragon.bpmrepo.core.repository.domain.business.AuthService;
import io.miragon.bpmrepo.core.repository.domain.facade.RepositoryFacade;
import io.miragon.bpmrepo.core.repository.domain.model.Repository;
import io.miragon.bpmrepo.core.shared.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShareFacade {

    private final AuthService authService;
    private final ArtifactService artifactService;
    private final ShareService shareService;

    private final RepositoryFacade repositoryFacade;

    public Shared shareWithRepository(final ShareWithRepositoryTO shareWithRepositoryTO) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.artifactService.getArtifactById(shareWithRepositoryTO.getArtifactId());
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        if (artifact.getRepositoryId() == shareWithRepositoryTO.getRepositoryId()) {
            //TODO: Throw custom error
            throw new RuntimeException("Cant share with parent repo");
        }
        return this.shareService.shareWithRepository(shareWithRepositoryTO);
    }

    public Shared updateShareWithRepository(final ShareWithRepositoryTO shareWithRepositoryTO) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.artifactService.getArtifactById(shareWithRepositoryTO.getArtifactId());
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        return this.shareService.updateShareWithRepository(shareWithRepositoryTO);
    }

    public Shared shareWithTeam(final ShareWithTeamTO shareWithTeamTO) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.artifactService.getArtifactById(shareWithTeamTO.getArtifactId());
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        if (artifact.getRepositoryId() == shareWithTeamTO.getTeamId()) {
            //TODO: Throw custom error
            throw new RuntimeException("Cant share with parent repo");
        }
        return this.shareService.shareWithTeam(shareWithTeamTO);
    }

    public Shared updateShareWithTeam(final ShareWithTeamTO shareWithTeamTO) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.artifactService.getArtifactById(shareWithTeamTO.getArtifactId());
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        return this.shareService.updateShareWithTeam(shareWithTeamTO);
    }

    public void unshareWithRepository(final String artifactId, final String repositoryId) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.artifactService.getArtifactById(artifactId);
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        this.shareService.deleteShareWithRepository(artifactId, repositoryId);
    }

    public void unshareWithTeam(final String artifactId, final String teamId) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.artifactService.getArtifactById(artifactId);
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        this.shareService.deleteShareWithTeam(artifactId, teamId);
    }


    public List<Artifact> getAllSharedArtifacts(final String userId) {
        log.debug("Checking Assignments");
        final List<Repository> repositories = this.repositoryFacade.getAllRepositories(userId);
        return this.shareService.getSharedArtifactsFromRepositories(repositories);
    }

    public List<Artifact> getSharedArtifacts(final String repositoryId) {
        log.debug("Checking Permissions");
        this.authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.MEMBER);
        final Repository repository = this.repositoryFacade.getRepository(repositoryId);
        return this.artifactService.getSharedArtifactsFromRepository(repository);
    }
}
