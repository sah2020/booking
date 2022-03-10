package com.exadel.demo_telegram_bot.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageSource messageSource;

    public String getMessage(String messageKey){
        return messageSource.getMessage(messageKey,null, Locale.ENGLISH);
    }
}
