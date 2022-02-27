package com.exadel.telegrambot.botapi;

import com.exadel.telegrambot.botapi.handlers.InputMessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isBookingState(currentState)) {
            return messageHandlers.get(BotState.CLIENT_BOOKING);
        }

        return messageHandlers.get(currentState);
    }

    private boolean isBookingState(BotState currentState) {
        return switch (currentState) {
            case CLIENT_BOOKING,
                    CLIENT_ASK_CITY,
                    CLIENT_ASK_OFFICE,
                    CLIENT_ASK_BOOKING_TYPE,
                    CLIENT_ASK_BOOKING_DATE,
                    CLIENT_ASK_BOOKING_FIRST_DAY,
                    CLIENT_ASK_BOOKING_LAST_DAY,
                    CLIENT_ASK_BOOKING_RECURRING,
                    CLIENT_ASK_PARKING,
                    CLIENT_ASK_PARKING_CONTINUE,
                    CLIENT_ASK_WORKPLACE_PARAMS -> true;
            default -> false;
        };
    }

}
