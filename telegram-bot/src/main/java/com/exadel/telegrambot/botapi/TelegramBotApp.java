package com.exadel.telegrambot.botapi;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TelegramBotApp extends TelegramWebhookBot {
    private String botPath;
    private String botUsername;
    private String botToken;

    private TelegramFacade telegramFacade;

    public TelegramBotApp(DefaultBotOptions options, TelegramFacade telegramFacade) {
        super(options);
        this.telegramFacade = telegramFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    public void sendMessage(long chatId, String textMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendInlineKeyBoardMessage(long chatId, String messageText, String buttonText, String callbackData) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton().setText(buttonText);

        if (callbackData != null) {
            keyboardButton.setCallbackData(callbackData);
        }

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(keyboardButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        try {
            execute(new SendMessage().setChatId(chatId).setText(messageText).setReplyMarkup(inlineKeyboardMarkup));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

  public void sendInlineKeyBoardMessageList(long chatId, String messageText, List<String> buttonTexts, String callbackData) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
      List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

      for (String buttonText : buttonTexts) {
          InlineKeyboardButton keyboardButton = new InlineKeyboardButton().setText(buttonText);
          List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
          keyboardButtonsRow1.add(keyboardButton);
          rowList.add(keyboardButtonsRow1);
      }
//        if (callbackData != null) {
//            keyboardButton.setCallbackData(callbackData);
//        }

        inlineKeyboardMarkup.setKeyboard(rowList);

        try {
            execute(new SendMessage().setChatId(chatId).setText(messageText).setReplyMarkup(inlineKeyboardMarkup));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendAnswerCallbackQuery(String callbackId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(message);
        answer.setShowAlert(true);

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendChangedInlineButtonText(CallbackQuery callbackQuery, String buttonText, String callbackData) {
        final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        final List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        final long message_id = callbackQuery.getMessage().getMessageId();
        final long chat_id = callbackQuery.getMessage().getChatId();
        final List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(buttonText).setCallbackData(callbackData));
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);

        EditMessageText editMessageText = new EditMessageText().setChatId(chat_id).setMessageId((int) (message_id)).
                setText(callbackQuery.getMessage().getText());

        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
