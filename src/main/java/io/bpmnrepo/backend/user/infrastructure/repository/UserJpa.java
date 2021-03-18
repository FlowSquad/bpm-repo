package io.bpmnrepo.backend.user.infrastructure.repository;

import io.bpmnrepo.backend.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<UserEntity, String> {

    UserEntity findByUserNameEquals(String userName);
    UserEntity findByEmail(String email);
    UserEntity findByUserIdEquals(String userId);
    Boolean existsUserEntityByUserName(String userName);
    Boolean existsUserEntityByEmail(String email);
    UserEntity findByApiKey(String apiKey);
}
