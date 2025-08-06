package com.spring.luispa.todo_list.exceptions;

public class TaskNotFoundException extends ResourceNotFoundException {

    public TaskNotFoundException(String message) {
        super(message);
    }

}
