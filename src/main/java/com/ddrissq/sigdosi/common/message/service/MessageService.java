package com.ddrissq.sigdosi.common.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class MessageService {

    private static final Locale LOCALE = LocaleContextHolder.getLocale();

    private final MessageSource messageSource;

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, LOCALE);
    }

    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, LOCALE);
    }

}
