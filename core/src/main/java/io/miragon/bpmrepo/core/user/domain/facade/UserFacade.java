package io.miragon.bpmrepo.core.user.domain.facade;

import io.miragon.bpmrepo.core.shared.exception.AccessRightException;
import io.miragon.bpmrepo.core.team.domain.model.Team;
import io.miragon.bpmrepo.core.team.domain.service.TeamService;
import io.miragon.bpmrepo.core.user.api.transport.UserOrTeamTO;
import io.miragon.bpmrepo.core.user.api.transport.UserUpdateTO;
import io.miragon.bpmrepo.core.user.domain.model.User;
import io.miragon.bpmrepo.core.user.domain.model.UserInfo;
import io.miragon.bpmrepo.core.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final TeamService teamService;

    public User createUser(final String username) {
        return this.userService.createUser(username);
    }

    public User updateUser(final UserUpdateTO userUpdateTO) {
        this.verifyUserIsChangingOwnProfile(userUpdateTO.getUserId());
        return this.userService.updateOrAdoptProperties(userUpdateTO);
    }

    private void verifyUserIsChangingOwnProfile(final String userId) {
        final String currentUserId = this.userService.getUserIdOfCurrentUser();
        if (!currentUserId.equals(userId)) {
            throw new AccessRightException("You can only change your own profile");
        }
    }
    

    public String getUserIdOfCurrentUser() {
        return this.userService.getUserIdOfCurrentUser();
    }


    public User getCurrentUser() {
        return this.userService.getCurrentUser();
    }

    public UserInfo getUserInfo() {
        return this.userService.getUserInfo();
    }

    public List<UserInfo> searchUsers(final String typedName) {
        return this.userService.searchUsers(typedName);
    }

    public List<UserInfo> getMultipleUsers(final List<String> userIds) {
        return this.userService.getMultipleUsers(userIds);
    }


    public List<UserOrTeamTO> searchUsersAndTeams(final String typedName) {
        log.debug("Querying list of matching users and teams");
        final List<UserInfo> userInfos = this.userService.searchUsers(typedName);
        final List<Team> teamTOS = this.teamService.searchTeams(typedName);
        List<UserOrTeamTO> usersAndTeams = new ArrayList<>();
        userInfos.stream().map(userInfo -> usersAndTeams.add(new UserOrTeamTO(userInfo.getId(), userInfo.getUsername(), false)));
        teamTOS.stream().map(teamTO -> usersAndTeams.add(new UserOrTeamTO(teamTO.getId(), teamTO.getName(), true)));
        return usersAndTeams;
    }

}
