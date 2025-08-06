package com.spring.luispa.todo_list.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL) Map<String, List<String>> errors) {

    public ApiErrorResponse(HttpStatus status, String message) {
        this(LocalDateTime.now(), status.value(), message, null);
    }

    public ApiErrorResponse(HttpStatus status, String message, Map<String, List<String>> errors) {
        this(LocalDateTime.now(), status.value(), message, errors);
    }
}
