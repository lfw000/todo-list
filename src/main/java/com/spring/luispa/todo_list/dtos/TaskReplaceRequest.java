package com.spring.luispa.todo_list.dtos;

import com.spring.luispa.todo_list.entities.TaskStatus;
import com.spring.luispa.todo_list.validation.ValidDateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TaskReplaceRequest(
        @NotBlank(message = "{NotBlank.taskCreateRequest.title}") @Size(min = 1, max = 256, message = "{Size.taskCreateRequest.title}") @Pattern(regexp = "^[^<>]*", message = "{error.input.specialCharacters}") String title,

        @NotBlank(message = "{NotBlank.taskReplaceRequest.description}") @Size(max = 512, message = "{Size.taskCreateRequest.description}") @Pattern(regexp = "^[^<>]*", message = "{error.input.specialCharacters}") String description,

        @NotBlank(message = "NotBlank.taskReplaceRequest.dueDate") @ValidDateTimeFormat(futureOrPresent = true) String dueDate,

        @NotNull TaskStatus status) {
}
