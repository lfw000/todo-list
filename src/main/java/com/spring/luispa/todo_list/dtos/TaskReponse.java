package com.spring.luispa.todo_list.dtos;

import java.time.LocalDateTime;

import com.spring.luispa.todo_list.entities.TaskStatus;

public record TaskReponse(
        Long id,

        String title,

        String description,

        LocalDateTime dueDate,

        TaskStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt) {
}
