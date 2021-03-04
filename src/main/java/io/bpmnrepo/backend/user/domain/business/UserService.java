package io.bpmnrepo.backend.user.domain.business;

import io.bpmnrepo.backend.shared.config.UserContext;
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

    public String getUserIdByUserName(String userName){
        UserEntity userEntity = userJpa.findByUsernameEquals(userName);
        String userId = userEntity.getUserId();
        return userId;
    }

    public String getUserIdOfCurrentUser(){
        String userName = userContext.getUserName();
        return this.getUserIdByUserName(userName);
    }



    public void saveToDb(UserEntity entity){
        this.userJpa.save(entity);
    }
}
