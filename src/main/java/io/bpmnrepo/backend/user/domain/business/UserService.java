package io.bpmnrepo.backend.user.domain.business;

import io.bpmnrepo.backend.shared.config.UserContext;
import io.bpmnrepo.backend.shared.exception.AccessRightException;
import io.bpmnrepo.backend.shared.exception.NameConflictException;
import io.bpmnrepo.backend.shared.exception.NameNotExistentException;
import io.bpmnrepo.backend.shared.exception.UserNotExistentException;
import io.bpmnrepo.backend.user.api.transport.*;
import io.bpmnrepo.backend.user.domain.exception.EmailAlreadyInUseException;
import io.bpmnrepo.backend.user.domain.exception.UsernameAlreadyInUseException;
import io.bpmnrepo.backend.user.domain.mapper.UserMapper;
import io.bpmnrepo.backend.user.domain.model.User;
import io.bpmnrepo.backend.user.infrastructure.entity.UserEntity;
import io.bpmnrepo.backend.user.infrastructure.repository.UserJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpa userJpa;
    private final UserContext userContext;
    private final UserMapper mapper;

    public void createUser(UserTO userTO) {
        User user = new User(userTO);
        checkIfUsernameIsAvailable(userTO.getUserName());
        checkIfEmailIsAvailable(userTO.getEmail());
        saveToDb(this.mapper.toEntity(user));
    }

    public void updateUser(UserUpdateTO userUpdateTO) {
        verifyUserIsChangingOwnProfile(userUpdateTO.getUserId());
        updateOrAdoptProperties(userUpdateTO);
    }

    private void updateOrAdoptProperties(UserUpdateTO userUpdateTO) {
        User user = this.getCurrentUser();
        if(userUpdateTO.getUsername() != null && !userUpdateTO.getUsername().equals(user.getUserName())){
            checkIfUsernameIsAvailable(userUpdateTO.getUsername());
            user.setUserName(userUpdateTO.getUsername());
        }
        if(userUpdateTO.getEmail() != null && !userUpdateTO.getEmail().equals(user.getEmail())){
            checkIfEmailIsAvailable(userUpdateTO.getEmail());
            user.setEmail(userUpdateTO.getEmail());
        }
        saveToDb(this.mapper.toEntity(user));
    }

    private void verifyUserIsChangingOwnProfile(String userId) {
        String currentUserId = this.getUserIdOfCurrentUser();
        if(!currentUserId.equals(userId)){
            throw new AccessRightException("You can only change your own profile");
        }
    }


    private void checkIfEmailIsAvailable(String email) {
        if(this.userJpa.existsUserEntityByEmail(email)){
            throw new EmailAlreadyInUseException(email);
        }
    }

    public String getUserIdByUsername(String username){
        UserEntity userEntity = this.userJpa.findByUserNameEquals(username);
        checkIfUserExists(userEntity);
        return userEntity.getUserId();
    }
    public void checkIfUserExists(UserEntity userEntity){
        if(userEntity == null){
            throw new NameNotExistentException();
        }
    }    public void checkIfUserExists(User user){
        if(user == null){
            throw new UserNotExistentException();
        }
    }

    public String getUserIdByEmail(String email){
        UserEntity userEntity = userJpa.findByEmail(email);
        checkIfUserExists(userEntity);
        String userId = userEntity.getUserId();
        return userId;
    }

    public String getUserIdOfCurrentUser(){
        String email = userContext.getUserEmail();
        UserEntity userEntity = userJpa.findByEmail(email);
        if(userEntity == null){
            userEntity = userJpa.findByUserNameEquals(email);
        }
        return userEntity.getUserId();
    }


    public void checkIfUsernameIsAvailable(String username){
        if(this.userJpa.existsUserEntityByUserName(username)){
            throw new UsernameAlreadyInUseException(username);
        }
    }

    public UserTO getApiKey(){
        final User currentUser = getCurrentUser();
        currentUser.updateApiKey();
        this.saveToDb(this.mapper.toEntity(currentUser));
        return this.mapper.toTO(currentUser);
    }

    public User getCurrentUser(){
        String email = userContext.getUserEmail();
        UserEntity userEntity = this.userJpa.findByEmail(email);
        return this.mapper.toModel(userEntity);
    }

    public UserEmailTO getUserEmail(){
        String email = userContext.getUserEmail();
        UserEmailTO userEmailTO = new UserEmailTO(email);
        return userEmailTO;
    }

    public UserInfoTO getUserInfo(){
        User user = getCurrentUser();
        checkIfUserExists(user);
        UserInfoTO userInfoTO = this.mapper.toInfoTO(user);
        return userInfoTO;
    }

    public List<UserInfoTO> searchUsers(String typedName){
        //Parameters correspond to (username, email) -> only one search field that queries both
        List<UserEntity> userEntities = this.userJpa.findAllByUserNameStartsWithOrEmailStartsWith(typedName, typedName);
        return userEntities.stream()
                .map(userEntity -> this.mapper.toInfoTO(this.mapper.toModel(userEntity)))
                .collect(Collectors.toList());
    }


    public void saveToDb(UserEntity entity){
        this.userJpa.save(entity);
    }
}
