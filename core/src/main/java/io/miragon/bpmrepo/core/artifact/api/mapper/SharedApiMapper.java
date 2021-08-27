package io.miragon.bpmrepo.core.artifact.api.mapper;

import io.miragon.bpmrepo.core.artifact.api.transport.ShareWithRepositoryTO;
import io.miragon.bpmrepo.core.artifact.api.transport.ShareWithTeamTO;
import io.miragon.bpmrepo.core.artifact.domain.model.ShareWithRepository;
import io.miragon.bpmrepo.core.artifact.domain.model.ShareWithTeam;
import io.miragon.bpmrepo.core.artifact.domain.model.Shared;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SharedApiMapper {

    ShareWithRepositoryTO mapToShareRepoTO(final Shared shared);

    ShareWithRepository mapToShareRepoModel(final ShareWithRepositoryTO to);

    ShareWithTeam mapToShareTeamModel(final ShareWithTeamTO to);

    ShareWithTeamTO mapToShareTeamTO(final Shared shared);

    List<ShareWithRepositoryTO> mapToShareRepoTO(final List<Shared> list);

    List<ShareWithTeamTO> mapToShareTeamTO(final List<Shared> list);
}
