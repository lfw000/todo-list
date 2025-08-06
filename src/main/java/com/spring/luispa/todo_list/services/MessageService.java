package com.spring.luispa.todo_list.services;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public static final String DEFAULT_ERROR = "An unexpected error ocurred";

    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        return getMessage(key, null, null);
    }

    public String getMessage(String key, Object[] args) {
        return getMessage(key, args, null);
    }

    public String getMessage(String key, String defaultKey) {
        return getMessage(key, null, defaultKey);
    }

    public String getMessage(String key, Object[] args, String defaultKey) {
        Locale locale = LocaleContextHolder.getLocale();

        String defaultMessage = defaultKey != null
                ? messageSource.getMessage(defaultKey, null, DEFAULT_ERROR, locale)
                : DEFAULT_ERROR;

        return messageSource.getMessage(key, args, defaultMessage, locale);
    }

}
