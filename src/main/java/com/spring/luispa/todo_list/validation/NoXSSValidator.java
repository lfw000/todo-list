package com.spring.luispa.todo_list.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoXSSValidator implements ConstraintValidator<NoXSS, String> {

    private static final String[] FORBIDDEN_PATTERNS = {
            "<", ">", "\"", "'", "script", "onerror", "onload", "javascript:"
    };

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        String lower = value.toLowerCase();

        for (String pattern : FORBIDDEN_PATTERNS) {
            if (lower.contains(pattern)) {
                return false;
            }
        }

        return true;
    }

}
