package com.exadel.demo_telegram_bot.handlers.client.buttons;

import com.exadel.demo_telegram_bot.service.MessageService;
import com.exadel.demo_telegram_bot.utils.TelegramButtonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
@RequiredArgsConstructor
public class ReplyKeyboardButtons {
    private final TelegramButtonUtils telegramButtonUtils;
    private final MessageService messageService;

    public ReplyKeyboardMarkup MAIN_MENU(){
        return telegramButtonUtils.replyKeyboardMarkup(
                telegramButtonUtils.KeyboardRow(
                        messageService.getMessage("button.myBooking"),
                        messageService.getMessage("button.bookingStart")
                )
        );
    }

    public ReplyKeyboardMarkup BACK_BUTTON(){
        return telegramButtonUtils.replyKeyboardMarkup(
                telegramButtonUtils.KeyboardRow(
                        messageService.getMessage("button.back")
                )
        );
    }

    public ReplyKeyboardRemove REMOVE_MARKUP() {
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        return replyKeyboardRemove;
    }
}
