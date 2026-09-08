package com.ddrissq.sigdosi.common.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class MessageService {

    private static final Locale LOCALE = Locale.getDefault();

    private final MessageSource messageSource;

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, LOCALE);
    }

    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LOCALE);
    }

}
