package io.bpmnrepo.backend.user.domain.mapper;

import io.bpmnrepo.backend.user.api.transport.UserTO;
import io.bpmnrepo.backend.user.domain.model.User;
import io.bpmnrepo.backend.user.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    User toModel(UserTO userTO);
    UserEntity toEntity(User model);
    User toModel(UserEntity userEntity);
    UserTO toTO(User user);

}
