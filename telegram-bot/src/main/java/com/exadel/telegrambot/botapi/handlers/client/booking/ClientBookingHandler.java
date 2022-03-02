package com.exadel.telegrambot.botapi.handlers.client.booking;

import com.exadel.telegrambot.botapi.BotState;
import com.exadel.telegrambot.botapi.TelegramBotApp;
import com.exadel.telegrambot.botapi.handlers.InputMessageHandler;
import com.exadel.telegrambot.cache.UserDataCache;
import com.exadel.telegrambot.cache.client.BookingDataCache;
import com.exadel.telegrambot.service.ReplyMessagesService;
import com.exadel.telegrambot.service.client.ClientBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Slf4j
@Component
public class ClientBookingHandler implements InputMessageHandler {

    private BookingDataCache bookingDataCache;
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    private ClientBookingService clientBookingService;
    private TelegramBotApp telegramBotApp;

    public ClientBookingHandler(BookingDataCache bookingDataCache, UserDataCache userDataCache, ReplyMessagesService messagesService, ClientBookingService clientBookingService, @Lazy TelegramBotApp telegramBotApp) {
        this.bookingDataCache = bookingDataCache;
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.clientBookingService = clientBookingService;
        this.telegramBotApp = telegramBotApp;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getChatId()).equals(BotState.CLIENT_BOOKING)) {
            userDataCache.setUsersCurrentBotState(message.getChatId(), BotState.CLIENT_ASK_CITY);
        }
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        long userChatId = inputMsg.getChatId();

        SendMessage replyToUser = messagesService.getReplyMessage(userChatId, "reply.booking.tryAgain");
        BookingRequestData bookingRequestData = bookingDataCache.getClientBookingData(userChatId);
        BotState currentBotState = userDataCache.getUsersCurrentBotState(userChatId);

        if (currentBotState.equals(BotState.CLIENT_ASK_CITY)) {
            List<String> cityListFromBackend = clientBookingService.getCityListFromBackend(userChatId);
            if (cityListFromBackend==null || cityListFromBackend.isEmpty()) {
                return messagesService.getReplyMessage(userChatId, "reply.booking.tryAgain");
            }
            telegramBotApp.sendInlineKeyBoardMessageList(userChatId, messagesService.getReplyText("reply.booking.city.choose"), cityListFromBackend, "");
            userDataCache.setUsersCurrentBotState(userChatId, BotState.CLIENT_ASK_BOOKING_TYPE);
        }

        bookingDataCache.saveClientBookingData(userChatId, bookingRequestData);
        return replyToUser;

    }

    @Override
    public BotState getHandlerName() {
        return BotState.CLIENT_BOOKING;
    }
}
