package io.bpmnrepo.backend.shared.exception;

public class NameNotExistentException extends RuntimeException{

    public NameNotExistentException(){
        super("This name does not exist.");
    }
}
