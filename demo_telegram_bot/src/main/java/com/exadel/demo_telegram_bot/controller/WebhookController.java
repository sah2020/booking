package com.exadel.demo_telegram_bot.controller;

import com.exadel.demo_telegram_bot.HotdeskBookingBotHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class WebhookController {
    private final HotdeskBookingBotHandler hotdeskBookingBotHandler;

    @PostMapping
    public void getUpdates(@RequestBody Update update){
        hotdeskBookingBotHandler.handleUpdate(update);
    }
}
