package io.bpmnrepo.backend.shared.exception;

//Thrown if an user tries to change its own access rights to a repository

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AccessRightException extends RuntimeException{
    public AccessRightException(final String message){
        super(message);
        log.warn(message);
    }
}
