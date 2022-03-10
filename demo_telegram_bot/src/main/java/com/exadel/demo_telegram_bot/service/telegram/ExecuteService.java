package com.exadel.demo_telegram_bot.service.telegram;

import com.exadel.demo_telegram_bot.config.TelegramBotConfig;
import com.exadel.demo_telegram_bot.response.TelegramResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Service
@RequiredArgsConstructor
public class ExecuteService {
    private final RestTemplate restTemplate;
    private final TelegramBotConfig telegramBotConfig;

    public TelegramResponse execute(SendMessage sendMessage){
        return execute("/sendMessage",sendMessage);
    }

    public TelegramResponse execute(SendDocument sendDocument){
        return execute("/sendDocument",sendDocument);
    }

    public TelegramResponse execute(EditMessageText editMessageText){
        return execute("/editMessageText",editMessageText);
    }

    public TelegramResponse execute(AnswerCallbackQuery answerCallbackQuery){
        return execute("/answerCallbackQuery",answerCallbackQuery);
    }

    private TelegramResponse execute(String methodType, Object object){
        return restTemplate.postForObject(telegramBotConfig.getBaseUrl()+telegramBotConfig.getBotToken()+methodType, object, TelegramResponse.class);
    }
}
