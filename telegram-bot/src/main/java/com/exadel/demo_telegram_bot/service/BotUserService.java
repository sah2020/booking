package com.exadel.demo_telegram_bot.service;

import com.exadel.demo_telegram_bot.model.BotUser;
import com.exadel.demo_telegram_bot.response.OkResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BotUserService {
    private final HashMap<String, BotUser> botUsers = new HashMap<>();

    @Value("${backend.baseUrl}")
    private String baseUrl;

    private final ExceptionResponseService exceptionResponseService;

    private final RestWebService restWebService;
    private final ObjectMapper objectMapper;

    public BotUser getUserByTelegramId(String telegramId){
        ResponseEntity<String> response = restWebService.getForEntity(baseUrl + "/user/" + telegramId, String.class);
        if (response.getStatusCode().toString().startsWith("2")){
            try {
                OkResponse okResponse = objectMapper.readValue(response.getBody(), OkResponse.class);
                BotUser botUser = objectMapper.convertValue(okResponse.getData(), BotUser.class);
                botUsers.put(telegramId, botUser);
                return botUser;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            exceptionResponseService.handleExceptionResponse(telegramId,response);
            return null;
        }
    }

    public BotUser getBotUserByHashMap(String chatId){
        return botUsers.get(chatId);
    }
}
