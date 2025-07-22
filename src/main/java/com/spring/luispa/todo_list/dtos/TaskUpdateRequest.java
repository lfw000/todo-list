package com.spring.luispa.todo_list.dtos;

import java.time.LocalDateTime;

import com.spring.luispa.todo_list.entities.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskUpdateRequest(
        @NotNull(message = "{NotNull.taskUpdateRequest.id}") Long id,

        @NotBlank(message = "{NotBlank.taskUpdateRequest.title}") @Size(min = 1, max = 256, message = "{Size.taskUpdateRequest.title}") String title,

        @Size(max = 512, message = "{Size.taskUpdateRequest.description}") String description,

        @FutureOrPresent(message = "{FutureOrPresent.taskUpdateRequest.dueDate}") LocalDateTime dueDate,

        TaskStatus status) {
}
