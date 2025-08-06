package com.spring.luispa.todo_list.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {

    private final MessageSource messageSource;

    public MessageResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, String fallbackKey, Locale locale) {
        return messageSource.getMessage(
                key,
                null,
                messageSource.getMessage(fallbackKey, null, locale),
                locale);
    }

}
