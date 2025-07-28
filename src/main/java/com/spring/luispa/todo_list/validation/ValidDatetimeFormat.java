package com.spring.luispa.todo_list.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Constraint(validatedBy = DateTimeFormatValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDatetimeFormat {
    String message() default "{error.dateTime.format}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    boolean futureOrPresent() default false;
}
