package com.spring.luispa.todo_list.exceptions;

import java.util.Objects;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(Objects.requireNonNull(message, "Message cannot be null"));
    }
}
