package com.spring.luispa.todo_list.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = NoXSSValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoXSS {
    String message() default "Not allowed characteres";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
