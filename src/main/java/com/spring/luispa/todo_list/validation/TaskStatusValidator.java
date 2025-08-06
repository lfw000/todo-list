package com.spring.luispa.todo_list.validation;

import com.spring.luispa.todo_list.entities.TaskStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaskStatusValidator implements ConstraintValidator<ValidTaskStatus, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Use with @NotNull for required fields
        if (value == null) {
            return true;
        }

        try {
            TaskStatus.valueOf(value.toUpperCase());

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
