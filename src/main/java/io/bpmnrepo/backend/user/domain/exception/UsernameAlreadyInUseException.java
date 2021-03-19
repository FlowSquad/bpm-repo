package io.bpmnrepo.backend.user.domain.exception;

import io.bpmnrepo.backend.shared.exception.NameConflictException;

public class UsernameAlreadyInUseException extends NameConflictException {
    public UsernameAlreadyInUseException(String username){
        super(String.format("The username '%s' is already in use", username));
    }
}
