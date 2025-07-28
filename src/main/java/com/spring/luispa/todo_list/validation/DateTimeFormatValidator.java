package com.spring.luispa.todo_list.validation;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class DateTimeFormatValidator implements ConstraintValidator<ValidDatetimeFormat, String> {

    private final MessageSource messageSource;

    private boolean futureOrPresent;

    public DateTimeFormatValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void initialize(ValidDatetimeFormat constraintAnnotation) {
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
                System.out.println("Entra a validar el rango de la FECHA");
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(messageSource.getMessage("error.dateTime.futureOrPresent",
                        null,
                        "The date must be in the present or future",
                        Locale.getDefault()))
                        .addConstraintViolation();

                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageSource.getMessage(
                    "error.dateTime.format",
                    null,
                    "Invalid date/time format",
                    Locale.getDefault()))
                    .addConstraintViolation();

            return false;
        }
    }

}
