package com.exadel.demo_telegram_bot.service.command;

import com.exadel.demo_telegram_bot.model.TelegramResponse;
import com.exadel.demo_telegram_bot.service.telegram.ExecuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommandService {
    private final ExecuteService executeService;

    public void onStartCommand(Update update){
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Hello from Exadel internship!");
        TelegramResponse execute = executeService.execute(sendMessage);
        System.out.println(execute.isOk());
    }
}
