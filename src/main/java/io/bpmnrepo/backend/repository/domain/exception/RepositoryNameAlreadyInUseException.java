package io.bpmnrepo.backend.repository.domain.exception;

import io.bpmnrepo.backend.shared.exception.NameConflictException;

public class RepositoryNameAlreadyInUseException extends NameConflictException {
    public RepositoryNameAlreadyInUseException(){
        super("Repository name duplicated - please choose another name");
    }
}
