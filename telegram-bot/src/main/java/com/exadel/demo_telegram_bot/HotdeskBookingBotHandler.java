package com.exadel.demo_telegram_bot;

import com.exadel.demo_telegram_bot.enums.UserRoleEnum;
import com.exadel.demo_telegram_bot.handlers.admin.AdminBotHandler;
import com.exadel.demo_telegram_bot.handlers.client.ClientBotHandler;
import com.exadel.demo_telegram_bot.handlers.manager.ManagerBotHandler;
import com.exadel.demo_telegram_bot.handlers.map_editor.MapEditorBotHandler;
import com.exadel.demo_telegram_bot.model.BotUser;
import com.exadel.demo_telegram_bot.model.Role;
import com.exadel.demo_telegram_bot.service.BotUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotdeskBookingBotHandler {
    private final BotUserService botUserService;
    private final AdminBotHandler adminBotHandler;
    private final ClientBotHandler clientBotHandler;
    private final ManagerBotHandler managerBotHandler;
    private final MapEditorBotHandler mapEditorBotHandler;
    private final ObjectMapper objectMapper;

    public void handleUpdate(Update update){
        if (update != null){
            String chatId = getChatId(update);
            log.info("Received update from chatId: {}", chatId);
            BotUser botUser = botUserService.getBotUserByHashMap(chatId); //this will get the user's current state in the bot

            if (botUser==null){
                botUser = botUserService.login(chatId);
            }

            if (botUser !=null){
                List<Role> roles = botUser.getRole();
                if (roles.size()>0){
                    switch (UserRoleEnum.valueOf(roles.get(0).getName())){
                        case ROLE_COMMON_USER-> clientBotHandler.handleUpdate(update);
                        case ROLE_ADMIN -> adminBotHandler.handleUpdate(update);
                        case ROLE_MANAGER -> managerBotHandler.handleUpdate(update);
                        case ROLE_MAP_EDITOR -> mapEditorBotHandler.handleUpdate(update);
                    }
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
