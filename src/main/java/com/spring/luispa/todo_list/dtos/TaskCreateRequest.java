package com.spring.luispa.todo_list.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
                @NotBlank(message = "{NotBlank.taskCreateRequest.title}") @Size(min = 1, max = 256, message = "{Size.taskCreateRequest.title}") String title,

                @Size(max = 512, message = "{Size.taskCreateRequest.description}") String description,

                @NotNull(message = "{NotNull.taskCreateRequest.dueDate}") @FutureOrPresent(message = "{FutureOrPresent.taskCreateRequest.dueDate}") LocalDateTime dueDate) {
}
