package io.bpmnrepo.backend.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<UserEntity, String> {

    UserEntity findByUsernameEquals(String userName);

}
