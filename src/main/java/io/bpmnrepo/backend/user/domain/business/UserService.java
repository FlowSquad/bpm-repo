package io.bpmnrepo.backend.user.domain.business;

import io.bpmnrepo.backend.shared.config.UserContext;
import io.bpmnrepo.backend.user.api.transport.UserTO;
import io.bpmnrepo.backend.user.domain.mapper.UserMapper;
import io.bpmnrepo.backend.user.domain.model.User;
import io.bpmnrepo.backend.user.infrastructure.entity.UserEntity;
import io.bpmnrepo.backend.user.infrastructure.repository.UserJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpa userJpa;
    private final UserContext userContext;
    private final UserMapper mapper;

    public void createUser(UserTO userTO) {
        User user = this.mapper.toModel(userTO);
        System.out.println(user.getUserName());
        if (checkIfUsernameIsAllowed(user.getUserName())) {
            throw new NameAlreadyInUseException(String.format("The username \"%s\" is not not available", user.getUserName()));
        } else {
            saveToDb(this.mapper.toEntity(user));
        }
    }

    public String getUserIdByUserName(String userName){
        UserEntity userEntity = userJpa.findByUserNameEquals(userName);
        String userId = userEntity.getUserId();
        return userId;
    }

    public String getUserIdOfCurrentUser(){
        String userName = userContext.getUserName();
        return this.getUserIdByUserName(userName);
    }

    //true: username already taken, false: username available
    public boolean checkIfUsernameIsAllowed(String userName){
        if(this.userJpa.existsUserEntityByUserName(userName) || userName == null || userName.isEmpty() || userName.isBlank()){
            return true;
        }
        else{
            return false;
        }
    }


    public void saveToDb(UserEntity entity){
        this.userJpa.save(entity);
    }
}
