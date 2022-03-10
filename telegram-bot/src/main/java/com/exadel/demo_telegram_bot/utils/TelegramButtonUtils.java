package com.exadel.demo_telegram_bot.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TelegramButtonUtils {
    //        Reply keyboard buttons

    public KeyboardRow KeyboardRow(String... buttons){
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(Arrays.asList(buttons));
        return keyboardRow;
    }

    public KeyboardRow featuredKeyboardRow(KeyboardButton... buttons){
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(Arrays.asList(buttons));
        return keyboardRow;
    }

    public ReplyKeyboardMarkup replyKeyboardMarkup(KeyboardRow... keyboardRows){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(Arrays.asList(keyboardRows));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

//    Inline keyboard buttons

    public InlineKeyboardButton inlineButton(String text, String callbackData){
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        return inlineKeyboardButton;
    }

    public List<InlineKeyboardButton> inlineKeyboardButtonRow(InlineKeyboardButton... inlineKeyboardButtons){
        return new ArrayList<>(Arrays.asList(inlineKeyboardButtons));
    }

    public final InlineKeyboardMarkup inlineKeyboard(List<List<InlineKeyboardButton>> rows){
        return new InlineKeyboardMarkup(rows);
    }

}
