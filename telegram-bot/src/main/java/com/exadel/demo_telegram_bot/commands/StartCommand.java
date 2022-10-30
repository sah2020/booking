package com.exadel.demo_telegram_bot.commands;

import com.exadel.demo_telegram_bot.cache.booking.BookingData;
import com.exadel.demo_telegram_bot.cache.booking.BookingDataCache;
import com.exadel.demo_telegram_bot.enums.BotStateEnum;
import com.exadel.demo_telegram_bot.enums.UserRoleEnum;
import com.exadel.demo_telegram_bot.service.BotStateService;
import com.exadel.demo_telegram_bot.service.telegram.ExecuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
@RequiredArgsConstructor
public class StartCommand {
    private final ExecuteService executeService;
    private final BotStateService botStateService;
    private final BookingDataCache bookingDataCache;

    public void execute(Update update, UserRoleEnum userRoleEnum){
        String chatId = update.getMessage().getChatId().toString();
        final User user = update.getMessage().getFrom();
        botStateService.clearState(chatId);
        botStateService.addState(chatId,BotStateEnum.MAIN_MENU);
        bookingDataCache.setBookingData(chatId, new BookingData());
        bookingDataCache.setConfirmationBooking(chatId, null);

        if (update.getMessage().getText()!=null && update.getMessage().getText().equals("/start")){
            switch (userRoleEnum){
                case ROLE_ADMIN -> executeService.execute(new SendMessage(chatId,"Welcome admin!"));
                case ROLE_MANAGER -> executeService.execute(new SendMessage(chatId,"Welcome manager!"));
                case ROLE_COMMON_USER -> executeService.execute(new SendMessage(chatId,"Welcome " + user.getFirstName()));
                case ROLE_MAP_EDITOR -> executeService.execute(new SendMessage(chatId,"Welcome map editor!"));
            }
        }
    }
}
