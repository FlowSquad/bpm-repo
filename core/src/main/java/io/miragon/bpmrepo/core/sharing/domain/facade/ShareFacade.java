package io.miragon.bpmrepo.core.sharing.domain.facade;

import io.miragon.bpmrepo.core.artifact.domain.mapper.ArtifactMapper;
import io.miragon.bpmrepo.core.artifact.domain.model.Artifact;
import io.miragon.bpmrepo.core.artifact.domain.service.ArtifactService;
import io.miragon.bpmrepo.core.repository.domain.facade.RepositoryFacade;
import io.miragon.bpmrepo.core.repository.domain.mapper.RepositoryMapper;
import io.miragon.bpmrepo.core.repository.domain.service.AuthService;
import io.miragon.bpmrepo.core.repository.domain.service.RepositoryService;
import io.miragon.bpmrepo.core.shared.enums.RoleEnum;
import io.miragon.bpmrepo.core.shared.exception.ObjectNotFoundException;
import io.miragon.bpmrepo.core.sharing.api.transport.SharedRepositoryTO;
import io.miragon.bpmrepo.core.sharing.api.transport.SharedTeamTO;
import io.miragon.bpmrepo.core.sharing.domain.model.ShareWithRepository;
import io.miragon.bpmrepo.core.sharing.domain.model.ShareWithTeam;
import io.miragon.bpmrepo.core.sharing.domain.service.ShareService;
import io.miragon.bpmrepo.core.team.domain.mapper.TeamMapper;
import io.miragon.bpmrepo.core.team.domain.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShareFacade {

    private final AuthService authService;
    private final ArtifactService artifactService;
    private final ShareService shareService;
    private final RepositoryService repositoryService;
    private final TeamService teamService;
    private final RepositoryFacade repositoryFacade;

    private final ArtifactMapper mapper;
    private final RepositoryMapper repositoryMapper;
    private final TeamMapper teamMapper;

    public ShareWithRepository shareWithRepository(final ShareWithRepository shareWithRepository) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.mapper.mapToModel(this.artifactService.getArtifactById(shareWithRepository.getArtifactId()).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")));
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        if (artifact.getRepositoryId() == shareWithRepository.getRepositoryId()) {
            //TODO: Throw custom error
            throw new RuntimeException("exception.cantShareWithParentRepo");
        }
        return this.shareService.shareWithRepository(shareWithRepository);
    }

    public void unshareWithRepository(final String artifactId, final String repositoryId) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.mapper.mapToModel(this.artifactService.getArtifactById(artifactId).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")));
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        this.shareService.deleteShareWithRepository(artifactId, repositoryId);
    }


    public ShareWithRepository updateShareWithRepository(final ShareWithRepository shareWithRepository) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.mapper.mapToModel(this.artifactService.getArtifactById(shareWithRepository.getArtifactId()).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")));
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        return this.shareService.updateShareWithRepository(shareWithRepository);
    }

    public ShareWithTeam shareWithTeam(final ShareWithTeam shareWithTeam) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.mapper.mapToModel(this.artifactService.getArtifactById(shareWithTeam.getArtifactId()).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")));
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        if (artifact.getRepositoryId() == shareWithTeam.getTeamId()) {
            //TODO: Throw custom error
            throw new RuntimeException("Cant share with parent repo");
        }
        return this.shareService.shareWithTeam(shareWithTeam);
    }

    public ShareWithTeam updateShareWithTeam(final ShareWithTeam shareWithTeam) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.mapper.mapToModel(this.artifactService.getArtifactById(shareWithTeam.getArtifactId()).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")));
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        return this.shareService.updateShareWithTeam(shareWithTeam);
    }


    public void unshareWithTeam(final String artifactId, final String teamId) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.mapper.mapToModel(this.artifactService.getArtifactById(artifactId).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")));
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        this.shareService.deleteShareWithTeam(artifactId, teamId);
    }


    public List<Artifact> getAllSharedArtifacts(final String userId) {
        log.debug("Checking Assignments");
        final List<String> repositoryIds = this.repositoryFacade.getAllRepositories(userId).stream().map(repository -> repository.getId()).collect(Collectors.toList());
        final List<String> artifactIds = this.shareService.getSharedArtifactIdsFromRepositories(repositoryIds);
        return this.artifactService.getAllArtifactsById(artifactIds);
    }

    public List<Artifact> getSharedArtifactsByType(final String userId, final String type) {
        log.debug("Checking Assignments");
        final List<String> repositoryIds = this.repositoryFacade.getAllRepositories(userId).stream().map(repository -> repository.getId()).collect(Collectors.toList());
        final List<String> artifactIds = this.shareService.getSharedArtifactIdsFromRepositories(repositoryIds);
        return this.artifactService.getAllArtifactsByIdAndType(artifactIds, type);
    }

    public List<Artifact> getArtifactsSharedWithRepository(final String repositoryId) {
        log.debug("Checking Permissions");
        this.authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER);
        final List<ShareWithRepository> shareRelations = this.shareService.getSharedArtifactsFromRepository(repositoryId);
        final List<String> sharedArtifactIds = shareRelations.stream().map(shareRelation -> shareRelation.getArtifactId()).collect(Collectors.toList());
        return this.artifactService.getAllArtifactsById(sharedArtifactIds);
    }

    public List<Artifact> getArtifactsSharedWithRepositoryByType(final String repositoryId, final String type) {
        log.debug("Checking Permissions");
        this.authService.checkIfOperationIsAllowed(repositoryId, RoleEnum.VIEWER);
        final List<ShareWithRepository> shareRelations = this.shareService.getSharedArtifactsFromRepository(repositoryId);
        final List<String> sharedArtifactIds = shareRelations.stream().map(shareRelation -> shareRelation.getArtifactId()).collect(Collectors.toList());
        return this.artifactService.getAllArtifactsByIdAndType(sharedArtifactIds, type);
    }


    public List<SharedRepositoryTO> getSharedRepositories(final String artifactId) {
        log.debug("Checking Permissions");
        final Artifact artifact = this.mapper.mapToModel(this.artifactService.getArtifactById(artifactId).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")));
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        final List<ShareWithRepository> shareWithRepositories = this.shareService.getSharedRepositories(artifactId);
        //Add the repository- and artifact names to the TOs to avoid sending additional requests from client
        final List<SharedRepositoryTO> sharedRepositoryTOS = shareWithRepositories.stream().map(shareWithRepository -> {
            final SharedRepositoryTO sharedRepositoryTO = new SharedRepositoryTO(
                    shareWithRepository.getArtifactId(),
                    shareWithRepository.getRepositoryId(),
                    shareWithRepository.getRole(),
                    this.artifactService.getArtifactById(shareWithRepository.getArtifactId()).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")).getName(),
                    this.repositoryMapper.mapToModel(
                            this.repositoryService.getRepository(shareWithRepository.getRepositoryId())
                                    .orElseThrow(() -> new ObjectNotFoundException("exception.repositoryNotFound"))).getName());
            return sharedRepositoryTO;
        }).collect(Collectors.toList());
        return sharedRepositoryTOS;
    }

    public List<SharedTeamTO> getSharedTeams(final String artifactId) {
        log.debug("Checking permissions");
        final Artifact artifact = this.mapper.mapToModel(this.artifactService.getArtifactById(artifactId).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")));
        this.authService.checkIfOperationIsAllowed(artifact.getRepositoryId(), RoleEnum.ADMIN);
        final List<ShareWithTeam> shareWithTeams = this.shareService.getSharedTeams(artifactId);

        final List<SharedTeamTO> sharedTeamTOS = shareWithTeams.stream().map(shareWithTeam -> {
            final SharedTeamTO shareWithTeamTO = new SharedTeamTO(
                    shareWithTeam.getArtifactId(),
                    shareWithTeam.getTeamId(),
                    shareWithTeam.getRole(),
                    this.artifactService.getArtifactById(shareWithTeam.getArtifactId()).orElseThrow(() -> new ObjectNotFoundException("exception.artifactNotFound")).getName(),
                    this.teamMapper.mapToModel(this.teamService.getTeam(shareWithTeam.getTeamId()).orElseThrow(() -> new ObjectNotFoundException("exception.teamNotFound"))).getName());
            return shareWithTeamTO;
        }).collect(Collectors.toList());
        return sharedTeamTOS;
    }
}
