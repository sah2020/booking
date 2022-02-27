package com.exadel.telegrambot.botapi;

import com.exadel.telegrambot.cache.UserDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class TelegramFacade {
    private UserDataCache userDataCache;
    private BotStateContext botStateContext;

    public TelegramFacade(UserDataCache userDataCache, BotStateContext botStateContext) {
        this.userDataCache = userDataCache;
        this.botStateContext = botStateContext;
    }

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        botState = switch (inputMsg) {
            case "/start" -> BotState.START;
            case "Start Booking" -> BotState.CLIENT_BOOKING;
            case "My Bookings" -> BotState.CLIENT_SHOW_BOOKINGS;
            default -> userDataCache.getUsersCurrentBotState(userId);
        };

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }

}
