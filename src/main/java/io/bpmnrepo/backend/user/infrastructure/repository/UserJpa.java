package io.bpmnrepo.backend.user.infrastructure.repository;

import io.bpmnrepo.backend.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJpa extends JpaRepository<UserEntity, String> {

    UserEntity findByUserNameEquals(String userName);
    UserEntity findByEmail(String email);
    UserEntity findByUserIdEquals(String userId);
    Boolean existsUserEntityByUserName(String userName);
    Boolean existsUserEntityByEmail(String email);
    UserEntity findByApiKey(String apiKey);
    //Both parameters are the same value (only one search field that queries for name AND email at the same time
    List<UserEntity> findAllByUserNameStartsWithOrEmailStartsWith(String typedName, String typedEmail);
}
