package io.bpmnrepo.backend.user.domain.exception;

import io.bpmnrepo.backend.shared.exception.NameConflictException;

public class EmailAlreadyInUseException extends NameConflictException {
    public EmailAlreadyInUseException(String email) {
        super(String.format("Email address '%s' already in use", email));
    }
}
