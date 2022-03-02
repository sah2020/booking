package com.exadel.demo_telegram_bot.service.telegram;

import com.exadel.demo_telegram_bot.service.command.StartCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TgService {
    private final StartCommandService startCommandService;

    public void handleUpdate(Update update){
        if (update.hasMessage()){
            String text = update.getMessage().getText();
            switch (text){
                case "/start" -> {
                    startCommandService.onStartCommand(update);
                }
                case "/help" -> {
                    System.out.println("There will be help service methods");
                }
            }
        }
    }
}
