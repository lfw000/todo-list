package com.spring.luispa.todo_list.dtos;

import com.spring.luispa.todo_list.validation.ValidDatetimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
        @NotBlank(message = "{NotBlank.taskCreateRequest.title}") @Size(min = 1, max = 256, message = "{Size.taskCreateRequest.title}") String title,

        @Size(max = 512, message = "{Size.taskCreateRequest.description}") String description,

        @NotBlank(message = "NotBlank.taskCreateRequest.dueDate") @ValidDatetimeFormat(futureOrPresent = true) String dueDate) {
}
