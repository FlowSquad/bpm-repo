package io.bpmnrepo.backend.user.infrastructure.repository;

import io.bpmnrepo.backend.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<UserEntity, String> {

    UserEntity findByUsernameEquals(String userName);

}
