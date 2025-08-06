package com.spring.luispa.todo_list.dtos;

import com.spring.luispa.todo_list.validation.NoXSS;
import com.spring.luispa.todo_list.validation.ValidDateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
                @NotBlank(message = "{NotBlank.taskCreateRequest.title}") @Size(min = 1, max = 256, message = "{Size.task.title}") @NoXSS(message = "{error.input.specialCharacters}") String title,

                @Size(max = 512, message = "{Size.task.description}") @NoXSS(message = "{error.input.specialCharacters}") String description,

                @NotBlank(message = "NotBlank.task.dueDate") @ValidDateTimeFormat(futureOrPresent = true) String dueDate) {
}
