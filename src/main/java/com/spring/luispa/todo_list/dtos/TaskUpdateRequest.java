package com.spring.luispa.todo_list.dtos;

import com.spring.luispa.todo_list.validation.NoXSS;
import com.spring.luispa.todo_list.validation.NotEmptyIfPresent;
import com.spring.luispa.todo_list.validation.ValidDateTimeFormat;
import com.spring.luispa.todo_list.validation.ValidTaskStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// "PATCH" method
public record TaskUpdateRequest(
                @NotNull(message = "{NotNull.task.id}") Long id,

                @NotEmptyIfPresent(message = "{NotEmptyIfPresent.task.title}") String title,

                @Size(max = 512, message = "{Size.task.description}") @NoXSS(message = "{error.input.specialCharacters}") String description,

                @ValidDateTimeFormat(futureOrPresent = true, message = "{error.dateTime.format}") String dueDate,

                @ValidTaskStatus(message = "{ValidTaskStatus.taskUpdateRequest.status}") String status) {
}
