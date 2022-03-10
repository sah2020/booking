package com.exadel.demo_telegram_bot;

import com.exadel.demo_telegram_bot.handlers.admin.AdminBotHandler;
import com.exadel.demo_telegram_bot.handlers.client.ClientBotHandler;
import com.exadel.demo_telegram_bot.handlers.manager.ManagerBotHandler;
import com.exadel.demo_telegram_bot.handlers.map_editor.MapEditorBotHandler;
import com.exadel.demo_telegram_bot.model.BotUser;
import com.exadel.demo_telegram_bot.service.BotUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotdeskBookingBotHandler {
    private final BotUserService botUserService;
    private final AdminBotHandler adminBotHandler;
    private final ClientBotHandler clientBotHandler;
    private final ManagerBotHandler managerBotHandler;
    private final MapEditorBotHandler mapEditorBotHandler;

    public void handleUpdate(Update update){
        if (update != null){
            String chatId = getChatId(update);
            log.info("Received update from chatId: {}", chatId);
            BotUser botUser = botUserService.getBotUserByHashMap(chatId);

            if (botUser==null){
                botUser = botUserService.getUserByTelegramId(chatId);
            }

            if (botUser !=null){
                switch (botUser.getRole()){
                    case  ROLE_COMMON_USER-> clientBotHandler.handleUpdate(update);
                    case ROLE_ADMIN -> adminBotHandler.handleUpdate(update);
                    case ROLE_MANAGER -> managerBotHandler.handleUpdate(update);
                    case ROLE_MAP_EDITOR -> mapEditorBotHandler.handleUpdate(update);
                }
            }
        }
    }

    private String getChatId(Update update){
        if (update.hasCallbackQuery()){
            log.info("Received callback query: {}", update.getCallbackQuery().getData());
            return update.getCallbackQuery().getMessage().getChatId().toString();
        }
        else if (update.hasMessage()){
            log.info("Received message: {}", update.getMessage().getText());
            return update.getMessage().getChatId().toString();
        }
        return "";
    }
}
