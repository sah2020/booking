package com.exadel.telegrambot.controller;

import com.exadel.telegrambot.botapi.TelegramFacade;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {
    private final TelegramFacade telegramFacade;

    public WebhookController(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramFacade.handleUpdate(update);
    }
}
