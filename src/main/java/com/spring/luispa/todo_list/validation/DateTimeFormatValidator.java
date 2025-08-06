package com.spring.luispa.todo_list.validation;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;

import com.spring.luispa.todo_list.services.MessageService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class DateTimeFormatValidator implements ConstraintValidator<ValidDateTimeFormat, String> {

    private final MessageService messageService;

    private boolean futureOrPresent;

    public DateTimeFormatValidator(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void initialize(ValidDateTimeFormat constraintAnnotation) {
        this.futureOrPresent = constraintAnnotation.futureOrPresent();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Use with @NotBlank for required fields
        if (value == null) {
            return true;
        }

        try {
            LocalDateTime date = LocalDateTime.parse(value);

            if (futureOrPresent && date.isBefore(LocalDateTime.now())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        messageService.getMessage("ValidDateTimeFormat.futureOrPresent",
                                "ValidDateTimeFormat.default"))
                        .addConstraintViolation();

                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    messageService.getMessage("ValidDateTimeFormat.format",
                            "ValidDateTimeFormat.default"))
                    .addConstraintViolation();

            return false;
        }
    }

}
