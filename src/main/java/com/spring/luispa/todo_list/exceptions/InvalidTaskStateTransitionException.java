package com.spring.luispa.todo_list.exceptions;

public class InvalidTaskStateTransitionException extends DomainException {

    public InvalidTaskStateTransitionException(String message) {
        super(message);
    }
}
