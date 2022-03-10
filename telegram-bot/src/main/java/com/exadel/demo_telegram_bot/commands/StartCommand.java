package com.exadel.demo_telegram_bot.commands;

import com.exadel.demo_telegram_bot.enums.BotStateEnum;
import com.exadel.demo_telegram_bot.enums.UserRoleEnum;
import com.exadel.demo_telegram_bot.service.BotStateService;
import com.exadel.demo_telegram_bot.service.telegram.ExecuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommand {
    private final ExecuteService executeService;
    private final BotStateService botStateService;

    public void execute(Update update, UserRoleEnum userRoleEnum){
        String chatId = update.getMessage().getChatId().toString();

        botStateService.clearState(chatId);
        botStateService.addState(chatId,BotStateEnum.MAIN_MENU);

        switch (userRoleEnum){
            case ROLE_ADMIN -> executeService.execute(new SendMessage(chatId,"Welcome admin!"));
            case ROLE_MANAGER -> executeService.execute(new SendMessage(chatId,"Welcome manager!"));
            case ROLE_COMMON_USER -> executeService.execute(new SendMessage(chatId,"Welcome user!"));
            case ROLE_MAP_EDITOR -> executeService.execute(new SendMessage(chatId,"Welcome map editor!"));
        }
    }
}
