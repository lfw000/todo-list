package com.spring.luispa.todo_list.validation;

import com.spring.luispa.todo_list.entities.TaskStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaskStatusValidator implements ConstraintValidator<ValidTaskStatus, TaskStatus> {

    @Override
    public boolean isValid(TaskStatus value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            TaskStatus status = TaskStatus.valueOf(value.name());

            return status != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
