package com.exadel.telegrambot.botapi.handlers.client.showBookings;

import com.exadel.telegrambot.botapi.BotState;
import com.exadel.telegrambot.botapi.handlers.InputMessageHandler;
import com.exadel.telegrambot.cache.UserDataCache;
import com.exadel.telegrambot.service.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class ShowBookingsHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService replyMessagesService;

    public ShowBookingsHandler(UserDataCache userDataCache, ReplyMessagesService replyMessagesService) {
        this.userDataCache = userDataCache;
        this.replyMessagesService = replyMessagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message message) {
        return replyMessagesService.getReplyMessage(message.getChatId(), "reply.message.error");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CLIENT_SHOW_BOOKINGS;
    }
}
