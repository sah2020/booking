package com.exadel.demo_telegram_bot.handlers.admin;

import com.exadel.demo_telegram_bot.commands.StartCommand;
import com.exadel.demo_telegram_bot.service.BotStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.exadel.demo_telegram_bot.enums.UserRoleEnum.ROLE_ADMIN;

@Component
@RequiredArgsConstructor
public class AdminBotHandler {
    private final StartCommand startCommand;
    private final BotStateService botStateService;

    public void handleUpdate(Update update){
        String chatId = update.getMessage().getChatId().toString();

        if (update.hasMessage()){
            String text = update.getMessage().getText();
            if (text.equals("/start")){
                startCommand.execute(update, ROLE_ADMIN);
            }
            switch (botStateService.peekState(chatId)){
                case CLIENT_ASK_BOOKING_DATE -> {
                }
            }
        }
    }
}
