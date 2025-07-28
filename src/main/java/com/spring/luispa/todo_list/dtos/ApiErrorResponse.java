package com.spring.luispa.todo_list.dtos;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        Map<String, String> errors) {

    public ApiErrorResponse(HttpStatus status, String message) {
        this(LocalDateTime.now(), status.value(), message, null);
    }

    public ApiErrorResponse(HttpStatus status, String message, Map<String, String> errors) {
        this(LocalDateTime.now(), status.value(), message, errors);
    }
}
