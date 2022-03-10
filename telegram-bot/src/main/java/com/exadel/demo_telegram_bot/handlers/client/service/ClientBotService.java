package com.exadel.demo_telegram_bot.handlers.client.service;

import com.exadel.demo_telegram_bot.handlers.client.buttons.ReplyKeyboardButtons;
import com.exadel.demo_telegram_bot.service.MessageService;
import com.exadel.demo_telegram_bot.service.telegram.ExecuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class ClientBotService {
    private final ReplyKeyboardButtons replyKeyboardButtons;
    private final ExecuteService executeService;
    private final MessageService messageService;

    public void sendMainMenu(String chatId){
        SendMessage sendMessage = new SendMessage(
                chatId,
                messageService.getMessage("reply.user.start"));
        sendMessage.setReplyMarkup(replyKeyboardButtons.MAIN_MENU());
        executeService.execute(sendMessage);
    }
}
